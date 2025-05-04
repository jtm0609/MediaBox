package com.example.presentation.feature.bookmark.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.presentation.model.BookmarkModel

@Composable
fun BookmarkGrid(
    bookmarks: List<BookmarkModel>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = bookmarks,
            key = { item -> item.url }
        ) { item ->
            BookmarkItemCard(
                item = item
            )
        }
    }
}