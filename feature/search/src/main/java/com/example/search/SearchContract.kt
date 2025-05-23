package com.example.search

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import com.example.search.model.SearchResultModel

class SearchContract {

    sealed class Event : UiEvent {
        data class OnSearchKeyword(val keyword: String) : Event()
        data class OnClickBookmark(val searchResultModel: SearchResultModel) : Event()
    }

    @Stable
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