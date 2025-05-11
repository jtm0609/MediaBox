package com.example.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core_ui.component.Progress
import com.example.search.component.EmptySearchGuide
import com.example.search.component.SearchBar
import com.example.search.component.SearchResultGrid
import com.example.search.model.SearchResultModel

@Composable
fun SearchScreen(
    onShowErrorSnackBar: (throwable: Throwable) -> Unit,
    onHideKeyboard: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val searchPagingItems = viewModel.searchResultFlow.collectAsLazyPagingItems()
    val effectFlow = viewModel.effect

    LaunchedEffect(effectFlow) {
        effectFlow.collect { effect ->
            when (effect) {
                is SearchContract.Effect.ShowError -> {
                    onShowErrorSnackBar(effect.throwable)
                }

                SearchContract.Effect.HideKeyBoard -> {
                    onHideKeyboard()
                }
            }
        }
    }

    SearchScreenContent(
        state = state,
        searchPagingItems = searchPagingItems,
        onSearchKeyword = {
            viewModel.setEvent(SearchContract.Event.OnSearchKeyword(it))
        },
        onBookmarkClick = { item ->
            viewModel.setEvent(SearchContract.Event.OnClickBookmark(item))
        }
    )
}

@Composable
private fun SearchScreenContent(
    state: SearchContract.State,
    searchPagingItems: LazyPagingItems<SearchResultModel>,
    onSearchKeyword: (String) -> Unit = {},
    onBookmarkClick: (SearchResultModel) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                onSearchKeyword = onSearchKeyword
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (state) {
                is SearchContract.State.Idle -> EmptySearchGuide()
                is SearchContract.State.Loading -> Progress()
                is SearchContract.State.Success -> {
                    SearchResultGrid(
                        searchItems = searchPagingItems,
                        onBookmarkClick = onBookmarkClick
                    )
                }
            }
        }
    }
}