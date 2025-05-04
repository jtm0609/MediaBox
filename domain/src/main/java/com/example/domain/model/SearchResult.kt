package com.example.domain.model

import java.time.LocalDateTime

data class SearchResult(
    val url: String,
    val dateTime: LocalDateTime,
    var bookMark: Boolean
)
