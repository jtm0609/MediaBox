package com.example.local.model

import com.example.data.model.BookmarkEntity
import com.example.local.LocalMapper
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkLocal(
    val url: String
) : LocalMapper<BookmarkEntity> {

    override fun toData(): BookmarkEntity =
        BookmarkEntity(
            url = url
        )
}

fun BookmarkEntity.toLocal(): BookmarkLocal =
    BookmarkLocal(
        url = url
    )
