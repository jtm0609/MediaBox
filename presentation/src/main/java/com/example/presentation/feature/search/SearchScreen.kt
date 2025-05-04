package com.example.presentation.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.presentation.common.base.Progress
import com.example.presentation.feature.search.composable.EmptySearchGuide
import com.example.presentation.feature.search.composable.SearchBar
import com.example.presentation.feature.search.composable.SearchResultGrid
import com.example.presentation.model.SearchItemModel

@Composable
fun SearchScreen(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable) -> Unit,
    onHideKeyboard: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val searchPagingItems = viewModel.searchResultFlow.collectAsLazyPagingItems()
    var queryState by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                query = queryState,
                onQueryChange = {
                    queryState = it
                    viewModel.setEvent(SearchContract.Event.OnSearchKeywordChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SearchContent(
                state = state,
                searchPagingItems = searchPagingItems,
                onBookmarkClick = { item ->
                    viewModel.setEvent(SearchContract.Event.OnClickBookmark(item))
                },
                onShowErrorSnackBar = onShowErrorSnackBar,
                onHideKeyboard = onHideKeyboard
            )
        }
    }
}

@Composable
private fun SearchContent(
    state: SearchContract.State,
    searchPagingItems: LazyPagingItems<SearchItemModel>,
    onBookmarkClick: (SearchItemModel) -> Unit,
    onShowErrorSnackBar: (throwable: Throwable) -> Unit,
    onHideKeyboard: () -> Unit
) {
    when (state) {
        is SearchContract.State.Idle -> EmptySearchGuide()
        is SearchContract.State.Loading -> Progress()
        is SearchContract.State.Error -> {
            onShowErrorSnackBar(state.throwable)
            onHideKeyboard()
        }
        is SearchContract.State.Success -> {
            onHideKeyboard()
            SearchResultGrid(
                searchItems = searchPagingItems,
                onBookmarkClick = onBookmarkClick
            )
        }
    }
}