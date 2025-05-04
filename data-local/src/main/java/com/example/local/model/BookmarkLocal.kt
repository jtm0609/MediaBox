package com.example.local.model

import com.example.data.model.BookmarkItemEntity
import com.example.local.LocalMapper
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkLocal(
    val url: String
) : LocalMapper<BookmarkItemEntity> {

    override fun toData(): BookmarkItemEntity =
        BookmarkItemEntity(
            url = url
        )
}

fun BookmarkItemEntity.toLocal(): BookmarkLocal =
    BookmarkLocal(
        url = url
    )
