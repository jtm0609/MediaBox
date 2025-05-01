package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.SearchItem
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultsUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): Flow<PagingData<SearchItem>> {
        return searchRepository.getSearchResults(query)
    }
} 