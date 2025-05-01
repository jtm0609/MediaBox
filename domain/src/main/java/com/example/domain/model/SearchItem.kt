package com.example.domain.model

import java.time.LocalDateTime

data class SearchItem(
    val url: String,
    val dateTime: LocalDateTime,
    val type: SearchItemType
)

enum class SearchItemType {
    IMAGE, VIDEO
}
