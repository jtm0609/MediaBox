package com.example.data.impl

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.Constants.NETWORK_PAGE_SIZE
import com.example.data.paging.RemoteSearchResultPagingSource
import com.example.domain.model.SearchItem
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchItemPagingSource: RemoteSearchResultPagingSource
) : SearchRepository {

    override suspend fun getSearchResults(
        query: String
    ): Flow<PagingData<SearchItem>> =
        Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false)
        ) {
            searchItemPagingSource.apply { this.keyWord = query }
        }.flow
}