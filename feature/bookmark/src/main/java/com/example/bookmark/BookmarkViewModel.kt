package com.example.bookmark

import androidx.lifecycle.viewModelScope
import com.example.bookmark.model.toBookmarkModel
import com.example.core_ui.base.BaseViewModel
import com.example.domain.usecase.GetBookmarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase
) : BaseViewModel<BookmarkContract.Event, BookmarkContract.State, BookmarkContract.Effect>() {

    init {
        getBookmarkList()
    }

    override fun createInitialState(): BookmarkContract.State = BookmarkContract.State.Idle

    override fun handleEvent(event: BookmarkContract.Event) {}

    private fun getBookmarkList() {
        viewModelScope.launch {
            getBookmarksUseCase()
                .onStart { BookmarkContract.State.Loading }
                .catch { BookmarkContract.Effect.ShowError(throwable = it) }
                .collect { bookmarkList ->
                    if (bookmarkList.isEmpty()) {
                        setState { BookmarkContract.State.Idle }
                    } else {
                        setState {
                            BookmarkContract.State.Success(bookmarkList = bookmarkList.map { it.toBookmarkModel() }.toPersistentList())
                        }
                    }
                }
        }
    }
}