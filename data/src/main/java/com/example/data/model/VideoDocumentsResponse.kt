package com.example.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDocumentsResponse(
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
    @SerialName("datetime")
    val dateTime: Instant,
    @SerialName("play_time")
    val playTime: Int,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("author")
    val author: String,
)
