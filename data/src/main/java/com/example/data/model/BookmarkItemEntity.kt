package com.example.data.model

import com.example.data.DataMapper
import com.example.domain.model.BookmarkItem
import com.example.domain.model.SearchItem
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

data class BookmarkItemEntity(
    val url: String
): DataMapper<BookmarkItem> {

    override fun toDomain(): BookmarkItem {
        return BookmarkItem(
            url = url
        )
    }
}

fun BookmarkItem.toData(): BookmarkItemEntity {
    return BookmarkItemEntity(
        url = url
    )
}
