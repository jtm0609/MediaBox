package com.example.presentation.model

import com.example.domain.model.SearchItem

data class BookmarkItemModel(
    val url: String
)

fun SearchItem.toBookmarkItemModel(): BookmarkItemModel {
    return BookmarkItemModel(
        url = url
    )
}
