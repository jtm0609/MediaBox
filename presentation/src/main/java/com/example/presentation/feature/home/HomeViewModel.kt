package com.example.presentation.feature.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.domain.usecase.GetSearchResultsUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchResultsUseCase: GetSearchResultsUseCase
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    override fun createInitialState(): HomeContract.State = HomeContract.State.initial()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnSearchQueryChanged -> {
                viewModelScope.launch { uiState.value.searchQuery.emit(value = event.query) }
            }

            is HomeContract.Event.OnSearchLoading -> setState { this.copy(isLoading = true) }
            is HomeContract.Event.OnSearchFinish -> setState { this.copy(isLoading = false) }

        }
    }

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            uiState.value.searchQuery
                .debounce(300L) // 300ms 디바운스
                .filter { it.isNotEmpty() && it.length >= 2 }
                .distinctUntilChanged()
                .collect { query ->
                    Log.d("taek", "query: $query")
                    getSearchResult(query = query)
                }
        }
    }


    private fun getSearchResult(query: String) {
        viewModelScope.launch {
            try {
                val result = searchResultsUseCase(query)
                    .map { pagingData ->
                        pagingData.map { searchItem ->
                            searchItem.toPresentation()
                        }
                    }
                setState {
                    copy(isLoading = false, searchResult = result)
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Search error", e)
                setState {
                    copy(isLoading = false, error = e)
                }
            }
        }
    }
}