package com.example.data.model

import com.example.data.DataMapper
import com.example.domain.model.Bookmark

data class BookmarkEntity(
    val url: String
): DataMapper<Bookmark> {

    override fun toDomain(): Bookmark {
        return Bookmark(
            url = url
        )
    }
}

fun Bookmark.toData(): BookmarkEntity {
    return BookmarkEntity(
        url = url
    )
}
