package com.example.presentation.feature.bookmark

import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetBookmarksUseCase
import com.example.presentation.common.base.BaseViewModel
import com.example.presentation.model.toBookmarkModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
                    setState {
                        BookmarkContract.State.Success(bookmarkList = bookmarkList.map { it.toBookmarkModel()})
                    }
                }
        }
    }
}