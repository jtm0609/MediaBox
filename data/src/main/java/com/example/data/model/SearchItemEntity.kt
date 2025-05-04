package com.example.data.model

import com.example.data.DataMapper
import com.example.domain.model.SearchResult
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime

data class SearchItemEntity(
    val url: String,
    val dateTime: LocalDateTime,
    var isBookMark: Boolean
): DataMapper<SearchResult> {

    override fun toDomain(): SearchResult {
        return SearchResult(
            url = url,
            dateTime = dateTime.toJavaLocalDateTime(),
            bookMark = isBookMark
        )
    }
}
