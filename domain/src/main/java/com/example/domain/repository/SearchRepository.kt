package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getInitSearchResults(
        query: String
    ): Flow<PagingData<SearchResult>>

    suspend fun getTotalSearchResults(
        query: String
    ): Flow<PagingData<SearchResult>>
}