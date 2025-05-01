package com.example.presentation.feature.home

import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetSearchResultUseCase
import com.example.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchResultUseCase: GetSearchResultUseCase
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    override fun createInitialState(): HomeContract.State = HomeContract.State.initial()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnSearchQueryChanged -> {
                setState { copy(searchQuery = MutableStateFlow(event.query)) }
            }
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
                    getSearchResult(query = query)
                }
        }
    }


    private fun getSearchResult(query: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            searchResultUseCase(query)
                .onSuccess {
                    setState { copy(isLoading = false, searchResult = it) }
                }
                .onFailure {
                    setState { copy(isLoading = false, error = it) }
                }
        }
    }
}