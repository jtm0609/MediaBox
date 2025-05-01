package com.example.presentation.feature.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.domain.usecase.GetSearchResultUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchResultsUseCase: GetSearchResultUseCase
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    override fun createInitialState(): HomeContract.State = HomeContract.State.initial()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnSearchKeywordChanged -> {
                viewModelScope.launch { uiState.value.searchQuery.emit(value = event.query) }
            }

            is HomeContract.Event.OnSearch -> {
                getSearchResult(event.query)
            }
        }
    }

    private fun getSearchResult(query: String) {
        viewModelScope.launch {
            try {
                setState { copy(isLoading = true) }
                searchResultsUseCase(query).withIndex().collect { (idx, result) ->
                    when (idx) {
                        0 -> {
                            launch {
                                setState { copy(isLoading = false) }
                                Log.d("taek", "collect 0")
                                // 첫 번째 결과 (초기 페이징 데이터)
                                result.collect { pagingData ->
                                    val initialResults = pagingData.map { it.toPresentation() }
                                    uiState.value.searchResult.emit(initialResults)
                                }
                            }
                        }

                        1 -> {
                            launch {
                                Log.d("taek", "collect 1")
                                // 두 번째 결과 (전체 페이징 데이터)
                                result.collect { pagingData ->
                                    val fullResults = pagingData.map { it.toPresentation() }
                                    uiState.value.searchResult.emit(fullResults)
                                }
                            }
                        }
                    }
                }
            } catch (exception: Exception) {
                Log.e("taek", "getSearchResult: ${exception.message}")
                setState { copy(isLoading = false, error = exception) }
            }
        }
    }
}