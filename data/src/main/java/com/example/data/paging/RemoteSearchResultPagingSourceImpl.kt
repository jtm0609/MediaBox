package com.example.data.paging

import androidx.paging.PagingState
import com.example.data.Constants.NETWORK_PAGE_SIZE
import com.example.data.Constants.STARTING_PAGE_INDEX
import com.example.data.api.ApiInterface
import com.example.domain.model.SearchItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class RemoteSearchResultPagingSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : RemoteSearchResultPagingSource() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            coroutineScope {
                // 두 API 호출을 병렬로 실행
                val imageDeferred = async { apiInterface.getImages(keyWord, position, params.loadSize) }
                val videoDeferred = async { apiInterface.getVideos(keyWord, position, params.loadSize) }

                val imageResponse = imageDeferred.await()
                val videoResponse = videoDeferred.await()

                val image = imageResponse.toDomain()
                val video = videoResponse.toDomain()

                val nextKey = if (imageResponse.meta.isEnd && videoResponse.meta.isEnd) {
                    null
                } else {
                    position + (params.loadSize / NETWORK_PAGE_SIZE)
                }

                LoadResult.Page(
                    data = (image + video).sortedByDescending { it.dateTime },
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return null
    }
}