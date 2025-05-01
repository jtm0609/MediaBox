package com.example.data.impl

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.Constants
import com.example.data.Constants.IMAGE_API_PAGE_MAX
import com.example.data.Constants.PAGE_SIZE
import com.example.data.Constants.VIDEO_API_PAGE_MAX
import com.example.data.datasource.SearchRemoteDataSource
import com.example.data.model.PagingResponse
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
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun getInitSearchResults(query: String): Flow<PagingData<SearchItem>> {
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
            }.catch { Log.e("taek","getInitSearchResults image initial fail:${it}") },
            flow {
                emit(
                    searchRemoteDataSource.getVideoSearchResult(
                        query = query,
                        page = 1,
                        size = Constants.VIDEO_API_SIZE_MAX,
                        sort = Constants.RECENCY
                    )
                )
            }.catch { Log.e("taek","getInitSearchResults video initial fail:${it}") }
        ) { imageResponse, videoResponse ->
            val image = imageResponse.toDomain()
            val video = videoResponse.toDomain()
            PagingResponse(searchResults = image + video)
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
                    imageIsEnd = imageResponse.meta.isEnd
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
                    videoIsEnd = imageResponse.meta.isEnd
                    videoPage++
                }
                emit(videoList)
            }) { imageList, videoList ->
            val searchResults = (imageList + videoList).sortedByDescending { it.dateTime }
            PagingResponse(searchResults = searchResults)
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