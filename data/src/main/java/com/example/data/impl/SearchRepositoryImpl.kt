package com.example.data.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.Constants.NETWORK_PAGE_SIZE
import com.example.data.paging.RemoteSearchResultPagingSourceImpl
import com.example.domain.model.SearchItem
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchItemPagingSource: RemoteSearchResultPagingSourceImpl
) : SearchRepository {

    override suspend fun getSearchResults(
        query: String
    ): Result<Flow<PagingData<SearchItem>>> {

        return runCatching {
            Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false)
            ) {
                searchItemPagingSource.apply { this.keyWord = query }
            }.flow
        }
    }
}