package com.example.presentation.feature.bookmark

import com.example.presentation.base.UiEffect
import com.example.presentation.base.UiEvent
import com.example.presentation.base.UiState
import com.example.presentation.model.BookmarkItemModel

class BookmarkContract {

    sealed class Event : UiEvent

    data class State(
        val isLoading: Boolean,
        val bookmarkList: List<BookmarkItemModel>,
        val error: Throwable?
    ) : UiState {
        companion object {
            fun initial(): State {
                return State(
                    isLoading = false,
                    bookmarkList = emptyList(),
                    error = null
                )
            }
        }
    }

    sealed class Effect : UiEffect
}