package com.example.bookmark.model

import com.example.domain.model.Bookmark

data class BookmarkModel(
    val url: String
)

fun Bookmark.toBookmarkModel(): BookmarkModel {
    return BookmarkModel(
        url = url
    )
}