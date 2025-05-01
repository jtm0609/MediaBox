package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getInitSearchResults(
        query: String
    ): Flow<PagingData<SearchItem>>

    suspend fun getTotalSearchResults(
        query: String
    ): Flow<PagingData<SearchItem>>
}