package com.example.presentation.feature.search.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.presentation.model.SearchResultModel

@Composable
fun SearchResultGrid(
    searchItems: LazyPagingItems<SearchResultModel>,
    onBookmarkClick: (SearchResultModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            count = searchItems.itemCount,
            key = { index ->
                searchItems[index]?.let { "${it.url}_${it.date}_${it.time}" } ?: index
            }
        ) { index ->
            searchItems[index]?.let { item ->
                SearchItemCard(
                    item = item,
                    onBookmarkClick = { onBookmarkClick(item) }
                )
            }
        }
    }
}