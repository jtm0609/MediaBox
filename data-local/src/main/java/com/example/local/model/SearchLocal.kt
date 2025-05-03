package com.example.local.model

import com.example.data.model.DocumentItemEntity
import com.example.local.LocalMapper
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SearchLocal(
    val url: String,
    val dateTime: LocalDateTime,
    val bookMark: Boolean
) : LocalMapper<DocumentItemEntity> {
    override fun toData(): DocumentItemEntity =
        DocumentItemEntity(
            url = url,
            dateTime = dateTime,
            bookMark = bookMark
        )

}

fun DocumentItemEntity.toLocal(): SearchLocal =
    SearchLocal(
        url = url,
        dateTime = dateTime,
        bookMark = bookMark
    )
