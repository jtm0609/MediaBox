package com.example.data.impl

import android.util.Log
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
import com.example.data.model.SearchItemCache
import com.example.data.paging.RemoteSearchResultPagingSource
import com.example.domain.model.SearchItem
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : SearchRepository {

    companion object {
        private const val TAG = "taek"
        private const val CACHE_EXPIRATION_TIME = 5 * 60 * 1000L // 5분을 밀리초로 변환
        private const val MAX_CACHE_SIZE = 20 // 최대 캐시 항목 수
    }

    // 검색 결과 캐시
    private val searchCache = mutableMapOf<String, SearchItemCache>()

    private fun <T> limitCacheSize(cache: MutableMap<String, T>) {
        if (cache.size > MAX_CACHE_SIZE) {
            // 가장 오래된 항목부터 제거 (단순화를 위해 첫 번째 항목 제거)
            val firstKey = cache.keys.firstOrNull()
            if (firstKey != null) {
                cache.remove(firstKey)
                Log.d(TAG, "캐시 크기 제한으로 항목 제거: $firstKey")
            }
        }
    }

    override suspend fun getInitSearchResults(query: String): Flow<PagingData<SearchItem>> {
        // 캐시에 해당 쿼리가 있고 만료되지 않았다면 빈 값을 방출 (스키핑)
        searchCache[query]?.let { cache ->
            if (!cache.isExpired(CACHE_EXPIRATION_TIME)) {
                Log.d(TAG, "초기 검색 스킵 (캐시 존재): 쿼리=$query")
                // 빈 리스트로 PagingData 생성
                val emptyResponse = PagingEntity(searchResults = emptyList())
                return Pager(
                    config = PagingConfig(
                        enablePlaceholders = false,
                        initialLoadSize = PAGE_SIZE,
                        pageSize = PAGE_SIZE
                    ),
                    pagingSourceFactory = { RemoteSearchResultPagingSource(emptyResponse) }
                ).flow
            }
        }

        Log.d(TAG, "초기 검색 결과 요청: 쿼리=$query")
        val pagingResponse = combine(
            flow {
                emit(
                    searchRemoteDataSource.getImageSearchResult(
                        query = query,
                        page = 1,
                        size = Constants.IMAGE_API_SIZE_MAX,
                        sort = Constants.RECENCY
                    )
                )
            }.catch { Log.e(TAG, "getInitSearchResults image initial fail:${it}") },
            flow {
                emit(
                    searchRemoteDataSource.getVideoSearchResult(
                        query = query,
                        page = 1,
                        size = Constants.VIDEO_API_SIZE_MAX,
                        sort = Constants.RECENCY
                    )
                )
            }.catch { Log.e(TAG, "getInitSearchResults video initial fail:${it}") }
        ) { imageResponse, videoResponse ->
            val image = imageResponse.toDomain()
            val video = videoResponse.toDomain()
            val searchResults = (image + video).sortedByDescending { it.dateTime }
            searchResults.map { it.bookMark = bookmarkLocalDataSource.isBookmarked(it.url) }
            PagingEntity(searchResults = searchResults)
        }.first()

        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE,
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = { RemoteSearchResultPagingSource(pagingResponse) }
        ).flow
    }

    override suspend fun getTotalSearchResults(query: String): Flow<PagingData<SearchItem>> {
        // 캐시에 해당 쿼리가 있고 만료되지 않았다면 캐시된 결과 반환
        searchCache[query]?.let { cache ->
            if (!cache.isExpired(CACHE_EXPIRATION_TIME)) {
                Log.d(TAG, "검색 결과 캐시 사용: 쿼리=$query")
                val cachedResponse = PagingEntity(searchResults = cache.searchItems)
                return Pager(
                    config = PagingConfig(
                        enablePlaceholders = false,
                        initialLoadSize = PAGE_SIZE,
                        pageSize = PAGE_SIZE
                    ),
                    pagingSourceFactory = { RemoteSearchResultPagingSource(cachedResponse) }
                ).flow
            }
        }

        Log.d(TAG, "전체 검색 결과 요청: 쿼리=$query")
        val pagingResponse = combine(
            flow {
                val imageList = mutableListOf<SearchItem>()
                var imagePage = 1
                var imageIsEnd = false
                while (imagePage < IMAGE_API_PAGE_MAX && !imageIsEnd) {
                    val imageResponse = searchRemoteDataSource.getImageSearchResult(
                        query = query,
                        page = imagePage,
                        size = Constants.IMAGE_API_SIZE_MAX,
                        sort = Constants.RECENCY
                    )
                    for (item in imageResponse.toDomain()) {
                        imageList.add(item)
                    }
                    imageIsEnd = imageResponse.isEnd
                    imagePage++
                }

                emit(imageList)
            },
            flow {
                val videoList = mutableListOf<SearchItem>()
                var videoPage = 1
                var videoIsEnd = false
                while (videoPage < VIDEO_API_PAGE_MAX && !videoIsEnd) {
                    val imageResponse = searchRemoteDataSource.getVideoSearchResult(
                        query = query,
                        page = videoPage,
                        size = Constants.IMAGE_API_SIZE_MAX,
                        sort = Constants.RECENCY
                    )
                    for (item in imageResponse.toDomain()) {
                        videoList.add(item)
                    }
                    videoIsEnd = imageResponse.isEnd
                    videoPage++
                }
                emit(videoList)
            }) { imageList, videoList ->
            val searchResults = (imageList + videoList).sortedByDescending { it.dateTime }
            searchResults.map { it.bookMark = bookmarkLocalDataSource.isBookmarked(it.url) }
            
            // 결과를 캐시에 저장
            searchCache[query] = SearchItemCache(searchResults, System.currentTimeMillis())
            limitCacheSize(searchCache)
            
            PagingEntity(searchResults = searchResults)
        }.first()

        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE,
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = { RemoteSearchResultPagingSource(pagingResponse) }
        ).flow
    }
}