package com.example.presentation.feature.bookmark

import com.example.presentation.common.base.UiEffect
import com.example.presentation.common.base.UiEvent
import com.example.presentation.common.base.UiState
import com.example.presentation.model.BookmarkModel

class BookmarkContract {

    sealed class Event : UiEvent

    sealed class State : UiState {
        data object Idle : State()
        data object Loading : State()
        data class Success(
            val bookmarkList: List<BookmarkModel>
        ) : State()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val throwable: Throwable) : Effect()
    }
}