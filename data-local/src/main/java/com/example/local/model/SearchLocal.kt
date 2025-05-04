package com.example.local.model

import com.example.data.model.BookmarkItemEntity
import com.example.local.LocalMapper
import kotlinx.serialization.Serializable

@Serializable
data class SearchLocal(
    val url: String
) : LocalMapper<BookmarkItemEntity> {

    override fun toData(): BookmarkItemEntity =
        BookmarkItemEntity(
            url = url
        )
}

fun BookmarkItemEntity.toLocal(): SearchLocal =
    SearchLocal(
        url = url
    )
