package com.example.bookmark

import androidx.compose.runtime.Stable
import com.example.bookmark.model.BookmarkModel
import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState
import kotlinx.collections.immutable.PersistentList

class BookmarkContract {

    sealed class Event : UiEvent

    @Stable
    sealed class State : UiState {
        data object Idle : State()
        data object Loading : State()
        data class Success(
            val bookmarkList: PersistentList<BookmarkModel>
        ) : State()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val throwable: Throwable) : Effect()
    }
}