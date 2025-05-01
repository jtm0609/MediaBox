package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.model.SearchItem

abstract class RemoteSearchResultPagingSource: PagingSource<Int, SearchItem>() {
    open var keyWord: String = ""

    abstract override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem>

    abstract override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int?
}