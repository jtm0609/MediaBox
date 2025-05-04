package com.example.data.model

import com.example.data.DataMapper
import com.example.domain.model.SearchItem
import kotlinx.datetime.toJavaLocalDateTime

data class SearchItemsEntity(
    val items: List<DocumentItemEntity>,
    val isEnd: Boolean
) : DataMapper<List<SearchItem>> {

    override fun toDomain(): List<SearchItem> =
        items.map {
            SearchItem(
                url = it.url,
                dateTime = it.dateTime.toJavaLocalDateTime(),
                bookMark = false
            )
        }
}

