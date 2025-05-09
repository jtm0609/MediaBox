package com.example.search

import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchResultsUseCase: GetSearchResultUseCase,
    private val saveBookmarkUseCase: SaveBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase
) : BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {

    val searchResultFlow: MutableStateFlow<PagingData<SearchResultModel>> =
        MutableStateFlow(PagingData.empty())
    private val _searchKeywordFlow = MutableSharedFlow<String>()

    init {
        setupSearchKeywordFlow()
    }

    override fun createInitialState(): SearchContract.State = SearchContract.State.Idle

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnSearchKeyword -> {
                emitSearchKeyword(event.keyword)
            }

            is SearchContract.Event.OnClickBookmark -> {
                toggleBookmark(event.searchResultModel)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchKeywordFlow() {
        viewModelScope.launch {
            _searchKeywordFlow
                .debounce(500)
                .distinctUntilChanged()
                .collect { keyword ->
                    if (keyword.isEmpty()) setState { SearchContract.State.Idle }
                    else getSearchResult(keyword)
                }
        }
    }

    private fun emitSearchKeyword(keyword: String) {
        viewModelScope.launch {
            _searchKeywordFlow.emit(keyword)
        }
    }

    private fun toggleBookmark(item: SearchResultModel) {
        viewModelScope.launch {
            try {
                val newBookmarkState = !item.bookMark
                if (item.bookMark) {
                    removeBookmarkUseCase(Bookmark(item.url))
                } else {
                    saveBookmarkUseCase(Bookmark(item.url))
                }
                updateBookmarkState(item, newBookmarkState)
            } catch (e: Exception) {
                setEffect { SearchContract.Effect.ShowError(e) }
            }
        }
    }

    private fun updateBookmarkState(item: SearchResultModel, newBookmarkState: Boolean) {
        viewModelScope.launch {
            try {
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
                    searchResultFlow.emit(updatedPagingData)
                }
            } catch (e: Exception) {
                setEffect { SearchContract.Effect.ShowError(e) }
            }
        }
    }

    private fun getSearchResult(keyword: String) {
        viewModelScope.launch {
            try {
                setState { SearchContract.State.Loading }
                searchResultsUseCase(keyword)
                    .collect { resultFlow ->
                        launch {
                            val cachedFlow = resultFlow.cachedIn(viewModelScope)

                            cachedFlow.collectLatest { pagingData ->
                                val mappedResults = pagingData.map { it.toSearchResultModel() }
                                setEffect { SearchContract.Effect.HideKeyBoard }
                                setState { SearchContract.State.Success(mappedResults) }
                                searchResultFlow.emit(mappedResults)
                            }
                        }
                    }
            } catch (e: Exception) {
                setEffect { SearchContract.Effect.ShowError(e) }
            }
        }
    }
}