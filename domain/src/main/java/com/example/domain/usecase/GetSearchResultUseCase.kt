package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.SearchResult
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {

    operator fun invoke(keyword: String): Flow<PagingData<SearchResult>> = flow {
        emit(searchRepository.getInitSearchResults(keyword).first())
        emit(searchRepository.getTotalSearchResults(keyword).first())
    }
}
