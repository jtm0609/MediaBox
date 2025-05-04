package com.example.presentation.model

import com.example.domain.model.BookmarkItem

data class BookmarkItemModel(
    val url: String
)

fun BookmarkItem.toBookmarkItemModel(): BookmarkItemModel {
    return BookmarkItemModel(
        url = url
    )
}
