package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.SearchItem
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {

    suspend operator fun invoke(query: String): Result<Flow<PagingData<SearchItem>>> =
        searchRepository.getSearchResults(query)
}