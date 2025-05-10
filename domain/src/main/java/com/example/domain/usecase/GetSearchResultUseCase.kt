package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.SearchResult
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetSearchResultUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {

    suspend operator fun invoke(keyword: String): Flow<PagingData<SearchResult>> = flowOf(
        searchRepository.getInitSearchResults(keyword),
        searchRepository.getTotalSearchResults(keyword)
    ).flattenMerge()
}
