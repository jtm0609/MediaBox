package com.example.remote.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDocumentsResponse(
    @SerialName("collection")
    val collection: String,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("display_sitename")
    val displaySiteName: String,
    @SerialName("doc_url")
    val docUrl: String,
    @SerialName("datetime")
    val dateTime: Instant
)
