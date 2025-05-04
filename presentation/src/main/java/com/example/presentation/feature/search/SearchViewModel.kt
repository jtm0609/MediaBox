package com.example.presentation.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.domain.model.BookmarkItem
import com.example.domain.usecase.GetSearchResultUseCase
import com.example.domain.usecase.RemoveBookmarkUseCase
import com.example.domain.usecase.SaveBookmarkUseCase
import com.example.presentation.common.base.BaseViewModel
import com.example.presentation.model.SearchItemModel
import com.example.presentation.model.toSearchItemModel
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

    val searchResultFlow: MutableStateFlow<PagingData<SearchItemModel>> =
        MutableStateFlow(PagingData.empty())
    private val _searchQueryFlow = MutableSharedFlow<String>()

    init {
        setupSearchQueryFlow()
    }

    override fun createInitialState(): SearchContract.State = SearchContract.State.Idle

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnSearchKeywordChanged -> {
                emitSearchQuery(event.query)
            }

            is SearchContract.Event.OnClickBookmark -> {
                toggleBookmark(event.searchItemModel)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchQueryFlow() {
        viewModelScope.launch {
            _searchQueryFlow
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isEmpty()) setState { SearchContract.State.Idle }
                    else getSearchResult(query)
                }
        }
    }

    private fun emitSearchQuery(query: String) {
        viewModelScope.launch {
            _searchQueryFlow.emit(query)
        }
    }

    private fun toggleBookmark(item: SearchItemModel) {
        viewModelScope.launch {
            try {
                val newBookmarkState = !item.bookMark
                if (item.bookMark) {
                    removeBookmarkUseCase(BookmarkItem(item.url))
                } else {
                    saveBookmarkUseCase(BookmarkItem(item.url))
                }
                updateBookmarkState(item, newBookmarkState)
            } catch (e: Exception) {
                setState { SearchContract.State.Error(e) }
            }
        }
    }

    private fun updateBookmarkState(item: SearchItemModel, newBookmarkState: Boolean) {
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
                setState { SearchContract.State.Error(e) }
            }
        }
    }

    private fun getSearchResult(query: String) {
        viewModelScope.launch {
            try {
                setState { SearchContract.State.Loading }
                searchResultsUseCase(query)
                    .collect { resultFlow ->
                        launch {
                            val cachedFlow = resultFlow.cachedIn(viewModelScope)

                            cachedFlow.collectLatest { pagingData ->
                                val mappedResults = pagingData.map { it.toSearchItemModel() }
                                setState { SearchContract.State.Success(mappedResults) }
                                searchResultFlow.emit(mappedResults)
                            }
                        }
                    }
            } catch (e: Exception) {
                setState { SearchContract.State.Error(e) }
            }
        }
    }
}