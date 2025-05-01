package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchResults(
        query: String
    ): Flow<PagingData<SearchItem>>
}