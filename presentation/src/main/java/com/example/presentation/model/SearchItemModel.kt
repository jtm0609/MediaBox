package com.example.presentation.model

import com.example.domain.model.SearchItem
import java.time.format.DateTimeFormatter

data class SearchItemModel(
    val url: String,
    val date: String,
    val time: String,
    val bookMark: Boolean
)

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
