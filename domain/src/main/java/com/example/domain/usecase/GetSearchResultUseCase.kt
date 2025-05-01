package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.SearchItem
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {

    operator fun invoke(query: String): Flow<Flow<PagingData<SearchItem>>> = flow {
        emit(searchRepository.getInitSearchResults(query))
        emit(searchRepository.getTotalSearchResults(query))
    }
}
