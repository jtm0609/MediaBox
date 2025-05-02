package com.example.data.local

import com.example.data.DataMapper
import com.example.domain.model.SearchItem
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SearchLocal(
    val url: String,
    val dateTime: LocalDateTime,
    val bookMark: Boolean
): DataMapper<SearchItem> {
    override fun toDomain(): SearchItem {
        return SearchItem(
            url = url,
            dateTime = dateTime.toJavaLocalDateTime(),
            bookMark = bookMark
        )
    }
}

fun SearchItem.toLocal(): SearchLocal {
    return SearchLocal(
        url = url,
        dateTime = dateTime.toKotlinLocalDateTime(),
        bookMark = bookMark
    )
}
