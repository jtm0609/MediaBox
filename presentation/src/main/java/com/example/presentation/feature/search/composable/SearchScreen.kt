package com.example.presentation.feature.search.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.presentation.common.composable.Progress
import com.example.presentation.feature.search.SearchContract
import com.example.presentation.feature.search.SearchViewModel
import com.example.presentation.model.SearchResultModel

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
        query = queryState,
        padding = padding,
        onQueryChange = {
            queryState = it
            viewModel.setEvent(SearchContract.Event.OnSearchKeywordChanged(it))
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
    query: String = "",
    padding: PaddingValues,
    onQueryChange: (String) -> Unit = {},
    onBookmarkClick: (SearchResultModel) -> Unit = {}
) {
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
                query = query,
                onQueryChange = onQueryChange
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