package com.example.bookmark.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookmark.model.BookmarkModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BookmarkGrid(
    bookmarks: PersistentList<BookmarkModel>
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

@Preview(showBackground = true)
@Composable
fun BookmarkGridPreview() {
    val sampleBookmarks = persistentListOf(
        BookmarkModel("https://search4.kakaocdn.net/argon/138x78_80_pr/E1H7Out9GDz"),
        BookmarkModel("https://search1.kakaocdn.net/argon/138x78_80_pr/GrZsTm9zhou"),
        BookmarkModel("https://search3.kakaocdn.net/argon/138x78_80_pr/AjJLoc9Cv77"),
        BookmarkModel("https://search4.kakaocdn.net/argon/138x78_80_pr/GYuq9yzMCRm"),
    )
    BookmarkGrid(bookmarks = sampleBookmarks)
}