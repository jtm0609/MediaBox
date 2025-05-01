package com.example.presentation.feature.home

import androidx.paging.PagingData
import com.example.presentation.base.UiEffect
import com.example.presentation.base.UiEvent
import com.example.presentation.base.UiState
import com.example.presentation.model.SearchItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class HomeContract {

    sealed class Event : UiEvent {
        data class OnSearchQueryChanged(val query: String) : Event()
        data object OnSearchLoading : Event()
        data object OnSearchFinish : Event()
    }

    data class State(
        val isLoading: Boolean,
        val searchResult: Flow<PagingData<SearchItemModel>>,
        val searchQuery: MutableStateFlow<String>,
        val error: Throwable
    ) : UiState {
        companion object {
            fun initial(): State {
                return State(
                    isLoading = false,
                    searchResult = flow{PagingData.empty<SearchItemModel>()},
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