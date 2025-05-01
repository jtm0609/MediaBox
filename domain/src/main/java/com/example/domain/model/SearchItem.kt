package com.example.domain.model

import kotlinx.datetime.Instant

data class SearchItem(
    val url: String,
    val dateTime: Instant
)
