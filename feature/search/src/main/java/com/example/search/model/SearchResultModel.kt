package com.example.search.model

import com.example.domain.model.SearchResult
import java.time.format.DateTimeFormatter

data class SearchResultModel(
    val url: String,
    val date: String,
    val time: String,
    val bookMark: Boolean
)

fun SearchResult.toSearchResultModel() : SearchResultModel {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return SearchResultModel(
        url = url,
        date = dateTime.format(dateFormatter),
        time = dateTime.format(timeFormatter),
        bookMark = bookMark
    )
}
