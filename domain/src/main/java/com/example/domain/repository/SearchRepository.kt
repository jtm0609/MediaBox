package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getInitSearchResults(
        keyword: String
    ): Flow<PagingData<SearchResult>>

    suspend fun getTotalSearchResults(
        keyword: String
    ): Flow<PagingData<SearchResult>>
}