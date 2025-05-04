package com.example.presentation.feature.search

import androidx.paging.PagingData
import com.example.presentation.common.base.UiEffect
import com.example.presentation.common.base.UiEvent
import com.example.presentation.common.base.UiState
import com.example.presentation.model.SearchResultModel

class SearchContract {

    sealed class Event : UiEvent {
        data class OnSearchKeywordChanged(val query: String) : Event()
        data class OnClickBookmark(val searchResultModel: SearchResultModel) : Event()
    }

    sealed class State : UiState {
        data object Idle : State()
        data object Loading : State()
        data class Success(val searchResultPagingList: PagingData<SearchResultModel>) : State()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val throwable: Throwable) : Effect()
        data object HideKeyBoard : Effect()
    }
}