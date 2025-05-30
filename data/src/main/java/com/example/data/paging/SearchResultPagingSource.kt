package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.Constants.PAGE_SIZE
import com.example.data.Constants.STARTING_PAGE_INDEX
import com.example.data.model.PagingEntity
import com.example.domain.model.SearchResult

class SearchResultPagingSource (
    private val pagingEntity: PagingEntity
) : PagingSource<Int, SearchResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResult> {

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

    override fun getRefreshKey(state: PagingState<Int, SearchResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getSearchResultByPage(
        pagingResponse: PagingEntity,
        page: Int
    ): List<SearchResult> {
        val chunkedList = pagingResponse.searchResults.chunked(PAGE_SIZE)
        return chunkedList[page - 1]
    }

    private fun calculateTotalPage(listSize: Int): Int {
        return (listSize / PAGE_SIZE) + 1
    }
}