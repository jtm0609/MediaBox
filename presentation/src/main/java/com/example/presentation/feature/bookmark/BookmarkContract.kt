package com.example.presentation.feature.bookmark

import com.example.presentation.common.base.UiEffect
import com.example.presentation.common.base.UiEvent
import com.example.presentation.common.base.UiState
import com.example.presentation.model.BookmarkItemModel

class BookmarkContract {

    sealed class Event : UiEvent

    sealed class State : UiState {
        data object Idle : State()
        data object Loading : State()
        data class Error(val throwable: Throwable) : State()
        data class Success(
            val bookmarkList: List<BookmarkItemModel>
        ) : State()
    }

    sealed class Effect : UiEffect
}