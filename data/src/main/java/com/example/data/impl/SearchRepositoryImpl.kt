package com.example.data.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.Constants
import com.example.data.Constants.IMAGE_API_PAGE_MAX
import com.example.data.Constants.PAGE_SIZE
import com.example.data.Constants.VIDEO_API_PAGE_MAX
import com.example.data.datasource.BookmarkLocalDataSource
import com.example.data.datasource.SearchRemoteDataSource
import com.example.data.model.PagingEntity
import com.example.data.model.SearchResultCache
import com.example.data.paging.SearchResultPagingSource
import com.example.domain.model.SearchResult
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : SearchRepository {

    companion object {
        private const val CACHE_EXPIRATION_TIME = 5 * 60 * 1000L
        private const val MAX_CACHE_SIZE = 20
    }

    // 검색 결과 캐시
    private val searchCache = mutableMapOf<String, SearchResultCache>()

    // 초기 검색 결과를 가져오는 함수 (캐시 있으면 빈 데이터 반환)
    override suspend fun getInitSearchResults(keyword: String): Flow<PagingData<SearchResult>> {
        checkCache(keyword, returnEmptyOnCacheHit = true)?.let {
            return createPager(it)
        }

        val pagingResponse = combine(
            getImageSearchFlow(keyword),
            getVideoSearchFlow(keyword)
        ) { imageResponse, videoResponse ->
            processSearchResults(imageResponse, videoResponse, keyword)
        }.first()

        return createPager(pagingResponse)
    }

    // 전체 검색 결과를 가져오는 함수 (캐시 있으면 캐시된 결과 반환)
    override suspend fun getTotalSearchResults(keyword: String): Flow<PagingData<SearchResult>> {
        checkCache(keyword)?.let {
            return createPager(it)
        }

        val pagingResponse = combine(
            getImageSearchFlow(keyword, fetchAll = true),
            getVideoSearchFlow(keyword, fetchAll = true)
        ) { imageList, videoList ->
            processSearchResults(imageList, videoList, keyword, shouldCache = true)
        }.first()

        return createPager(pagingResponse)
    }

    // 이미지 검색 결과를 가져오는 Flow 생성
    private fun getImageSearchFlow(keyword: String, fetchAll: Boolean = false) = flow {
        if (fetchAll) {
            val imageList = mutableListOf<SearchResult>()
            var page = 1
            var isEnd = false

            while (page < IMAGE_API_PAGE_MAX && !isEnd) {
                val response = searchRemoteDataSource.getImageSearchResult(
                    keyword = keyword,
                    page = page,
                    size = Constants.IMAGE_API_SIZE_MAX,
                    sort = Constants.RECENCY
                )
                imageList.addAll(response.toDomain())
                isEnd = response.isEnd
                page++
            }
            emit(imageList)
        } else {
            val response = searchRemoteDataSource.getImageSearchResult(
                keyword = keyword,
                page = 1,
                size = Constants.IMAGE_API_SIZE_MAX,
                sort = Constants.RECENCY
            )
            emit(response.toDomain())
        }
    }

    // 비디오 검색 결과를 가져오는 Flow 생성
    private fun getVideoSearchFlow(keyword: String, fetchAll: Boolean = false) = flow {
        if (fetchAll) {
            val videoList = mutableListOf<SearchResult>()
            var page = 1
            var isEnd = false

            while (page < VIDEO_API_PAGE_MAX && !isEnd) {
                val response = searchRemoteDataSource.getVideoSearchResult(
                    keyword = keyword,
                    page = page,
                    size = Constants.VIDEO_API_SIZE_MAX,
                    sort = Constants.RECENCY
                )
                videoList.addAll(response.toDomain())
                isEnd = response.isEnd
                page++
            }
            emit(videoList)
        } else {
            val response = searchRemoteDataSource.getVideoSearchResult(
                keyword = keyword,
                page = 1,
                size = Constants.VIDEO_API_SIZE_MAX,
                sort = Constants.RECENCY
            )
            emit(response.toDomain())
        }
    }

    // 이미지와 비디오 검색 결과 처리 및 북마크 상태 설정
    private suspend fun processSearchResults(
        imageData: List<SearchResult>,
        videoData: List<SearchResult>,
        keyword: String,
        shouldCache: Boolean = false
    ): PagingEntity {
        val searchResults = (imageData + videoData).sortedByDescending { it.dateTime }
        searchResults.forEach { it.bookMark = bookmarkLocalDataSource.isBookmarked(it.url) }

        if (shouldCache) {
            searchCache[keyword] = SearchResultCache(searchResults, System.currentTimeMillis())
            limitCacheSize(searchCache)
        }

        return PagingEntity(searchResults = searchResults)
    }

    // PagingEntity로 Pager 생성
    private fun createPager(pagingEntity: PagingEntity): Flow<PagingData<SearchResult>> =
        Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE,
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = { SearchResultPagingSource(pagingEntity) }
        ).flow

    // 캐시 확인 및 유효한 결과 반환
    private fun checkCache(keyword: String, returnEmptyOnCacheHit: Boolean = false): PagingEntity? {
        searchCache[keyword]?.let { cache ->
            if (!cache.isExpired(CACHE_EXPIRATION_TIME)) {
                val searchResults = if (returnEmptyOnCacheHit) emptyList() else cache.searchResults
                return PagingEntity(searchResults = searchResults)
            }
        }
        return null
    }

    // 캐시 크기 제한 (최대 크기 초과 시 가장 오래된 항목 제거)
    private fun <T> limitCacheSize(cache: MutableMap<String, T>) {
        if (cache.size > MAX_CACHE_SIZE) {
            cache.keys.firstOrNull()?.let { oldestKey ->
                cache.remove(oldestKey)
            }
        }
    }
}