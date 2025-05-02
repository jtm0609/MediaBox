package com.example.presentation.feature.home

import androidx.paging.PagingData
import com.example.presentation.base.UiEffect
import com.example.presentation.base.UiEvent
import com.example.presentation.base.UiState
import com.example.presentation.model.SearchItemModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeContract {

    sealed class Event : UiEvent {
        data class OnSearchKeywordChanged(val query: String) : Event()
        data class OnSearch(val query: String) : Event()
        data class OnClickBookmark(val searchItemModel: SearchItemModel) : Event()
    }

    data class State(
        val isLoading: Boolean,
        val searchResult: MutableStateFlow<PagingData<SearchItemModel>>,
        val searchQuery: MutableStateFlow<String>,
        val error: Throwable?
    ) : UiState {
        companion object {
            fun initial(): State {
                return State(
                    isLoading = false,
                    searchResult = MutableStateFlow(PagingData.empty()),
                    searchQuery = MutableStateFlow(""),
                    error = null
                )
            }
        }
    }

    sealed class Effect : UiEffect {
        data object GoBookmark : Effect()
    }
}