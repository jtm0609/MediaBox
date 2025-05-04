package com.example.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.Constants.PAGE_SIZE
import com.example.data.Constants.STARTING_PAGE_INDEX
import com.example.data.model.PagingEntity
import com.example.domain.model.SearchItem

class RemoteSearchResultPagingSource (
    private val pagingEntity: PagingEntity
) : PagingSource<Int, SearchItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {

        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val chunkedList = getSearchResultByPage(
                pagingResponse = pagingEntity,
                page = page
            )
            LoadResult.Page(
                data = chunkedList,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == calculateTotalPage(pagingEntity.searchResults.size)) null else page + 1
            )
        } catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        Log.d("taek","getRefresh!!")
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getSearchResultByPage(
        pagingResponse: PagingEntity,
        page: Int
    ): List<SearchItem> {
        val chunkedList = pagingResponse.searchResults.chunked(PAGE_SIZE)
        return chunkedList[page - 1]
    }

    private fun calculateTotalPage(listSize: Int): Int {
        return (listSize / PAGE_SIZE) + 1
    }
}