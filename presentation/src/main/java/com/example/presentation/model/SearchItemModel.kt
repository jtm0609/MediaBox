package com.example.presentation.model

import com.example.domain.model.SearchItem
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

data class SearchItemModel(
    val url: String,
    val date: String,
    val time: String
)

fun SearchItem.toPresentation() : SearchItemModel {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return SearchItemModel(
        url = url,
        date = dateTime.format(dateFormatter),
        time = dateTime.format(timeFormatter)
    )
}
