package com.example.presentation.model

import com.example.domain.model.SearchItem
import com.example.presentation.PresentationMapper
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

data class SearchItemModel(
    val url: String,
    val date: String,
    val time: String,
    val bookMark: Boolean
): PresentationMapper<SearchItem> {
    override fun toDomain(): SearchItem {
        val dateTimeString = "$date $time"
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        
        return SearchItem(
            url = url,
            dateTime = dateTime,
            bookMark = bookMark
        )
    }
}


fun SearchItem.toSearchItemModel() : SearchItemModel {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return SearchItemModel(
        url = url,
        date = dateTime.format(dateFormatter),
        time = dateTime.format(timeFormatter),
        bookMark = bookMark
    )
}
