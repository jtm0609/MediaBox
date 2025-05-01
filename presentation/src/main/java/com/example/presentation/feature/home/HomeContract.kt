package com.example.presentation.feature.home

import androidx.compose.runtime.MutableState
import androidx.paging.PagingData
import com.example.domain.model.SearchItem
import com.example.presentation.base.UiEffect
import com.example.presentation.base.UiEvent
import com.example.presentation.base.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class HomeContract {

    sealed class Event : UiEvent {
        data class OnSearchQueryChanged(val query: String) : Event()
    }

    data class State(
        val isLoading: Boolean,
        val searchResult: Flow<PagingData<SearchItem>>,
        val searchQuery: MutableStateFlow<String>,
        val error: Throwable
    ) : UiState {
        companion object {
            fun initial(): State {
                return State(
                    isLoading = false,
                    searchResult = flow{PagingData.empty<SearchItem>()},
                    searchQuery = MutableStateFlow(""),
                    error = Throwable()
                )
            }
        }
    }

    sealed class Effect : UiEffect {
        data object GoDetailRoute : Effect()
    }
}