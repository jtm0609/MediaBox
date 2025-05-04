package com.example.data.model

import com.example.data.DataMapper
import com.example.domain.model.SearchResult
import kotlinx.datetime.toJavaLocalDateTime

data class SearchResultEntity(
    val items: List<SearchItemEntity>,
    val isEnd: Boolean
) : DataMapper<List<SearchResult>> {

    override fun toDomain(): List<SearchResult> =
        items.map {
            SearchResult(
                url = it.url,
                dateTime = it.dateTime.toJavaLocalDateTime(),
                bookMark = false
            )
        }
}

