package com.example.presentation.feature.bookmark

import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetBookmarksUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.toBookmarkItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    val getBookmarksUseCase: GetBookmarksUseCase
) : BaseViewModel<BookmarkContract.Event, BookmarkContract.State, BookmarkContract.Effect>() {

    init {
        getBookmarkList()
    }

    override fun createInitialState(): BookmarkContract.State = BookmarkContract.State.initial()

    override fun handleEvent(event: BookmarkContract.Event) {}

    private fun getBookmarkList() {
        setState { this.copy(isLoading = true) }
        viewModelScope.launch {
            getBookmarksUseCase().onSuccess { bookmarkList ->
                setState {
                    this.copy(
                        isLoading = false,
                        bookmarkList = bookmarkList.map { it.toBookmarkItemModel() })
                }
            }.onFailure {
                setState { this.copy(isLoading = false, error = it) }
            }
        }
    }
}