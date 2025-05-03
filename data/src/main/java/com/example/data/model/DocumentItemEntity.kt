package com.example.data.model

import com.example.data.DataMapper
import com.example.domain.model.SearchItem
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

data class DocumentItemEntity(
    val url: String,
    val dateTime: LocalDateTime,
    var bookMark: Boolean
): DataMapper<SearchItem> {
    override fun toDomain(): SearchItem {
        return SearchItem(
            url = url,
            dateTime = dateTime.toJavaLocalDateTime(),
            bookMark = bookMark
        )
    }
}

fun SearchItem.toData(): DocumentItemEntity {
    return DocumentItemEntity(
        url = url,
        dateTime = dateTime.toKotlinLocalDateTime(),
        bookMark = bookMark
    )
}
