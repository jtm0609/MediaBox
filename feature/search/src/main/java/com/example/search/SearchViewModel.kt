package com.example.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.core_ui.base.BaseViewModel
import com.example.domain.model.Bookmark
import com.example.domain.usecase.GetSearchResultUseCase
import com.example.domain.usecase.RemoveBookmarkUseCase
import com.example.domain.usecase.SaveBookmarkUseCase
import com.example.search.model.SearchResultModel
import com.example.search.model.toSearchResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchResultsUseCase: GetSearchResultUseCase,
    private val saveBookmarkUseCase: SaveBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase
) : BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {

    private val _searchResultFlow =
        MutableStateFlow<PagingData<SearchResultModel>>(PagingData.empty())
    val searchResultFlow: StateFlow<PagingData<SearchResultModel>> = _searchResultFlow.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        setEffect { SearchContract.Effect.ShowError(throwable) }
        setEffect { SearchContract.Effect.HideKeyBoard }
    }

    override fun createInitialState(): SearchContract.State = SearchContract.State.Idle

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnSearchKeyword -> handleSearchKeyword(event.keyword)
            is SearchContract.Event.OnClickBookmark -> handleBookmarkClick(event.searchResultModel)
        }
    }

    private fun handleSearchKeyword(keyword: String) {
        if (keyword.isEmpty()) {
            setState { SearchContract.State.Idle }
            return
        }
        getSearchResult(keyword)
    }

    private fun handleBookmarkClick(item: SearchResultModel) {
        viewModelScope.launch(exceptionHandler) {
            val newBookmarkState = !item.bookMark
            if (item.bookMark) {
                removeBookmarkUseCase(Bookmark(item.url))
            } else {
                saveBookmarkUseCase(Bookmark(item.url))
            }
            updateBookmarkState(item, newBookmarkState)
        }
    }

    private fun updateBookmarkState(item: SearchResultModel, newBookmarkState: Boolean) {
        viewModelScope.launch(exceptionHandler) {
            val currentState = uiState.value
            if (currentState is SearchContract.State.Success) {
                val updatedPagingData = currentState.searchResultPagingList.map { searchItem ->
                    if (searchItem.url == item.url) {
                        searchItem.copy(bookMark = newBookmarkState)
                    } else {
                        searchItem
                    }
                }
                setState { SearchContract.State.Success(updatedPagingData) }
                _searchResultFlow.emit(updatedPagingData)
            }
        }
    }

    private fun getSearchResult(keyword: String) {
        viewModelScope.launch(exceptionHandler) {
            setState { SearchContract.State.Loading }
            searchResultsUseCase(keyword)
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    val mappedResults = pagingData.map { it.toSearchResultModel() }
                    setEffect { SearchContract.Effect.HideKeyBoard }
                    setState { SearchContract.State.Success(mappedResults) }
                    _searchResultFlow.emit(mappedResults)
                }
        }
    }
}