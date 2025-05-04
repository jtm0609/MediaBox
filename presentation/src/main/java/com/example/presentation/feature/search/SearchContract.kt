package com.example.presentation.feature.search

import androidx.paging.PagingData
import com.example.presentation.common.base.UiEffect
import com.example.presentation.common.base.UiEvent
import com.example.presentation.common.base.UiState
import com.example.presentation.model.SearchItemModel

class SearchContract {

    sealed class Event : UiEvent {
        data class OnSearchKeywordChanged(val query: String) : Event()
        data class OnClickBookmark(val searchItemModel: SearchItemModel) : Event()
    }

    sealed class State : UiState {
        data object Idle : State()
        data object Loading : State()
        data class Error(val throwable: Throwable) : State()
        data class Success(val searchResultPagingList: PagingData<SearchItemModel>) : State()
    }

    sealed class Effect : UiEffect
}