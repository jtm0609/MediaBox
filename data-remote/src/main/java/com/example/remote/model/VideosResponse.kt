package com.example.remote.model

import com.example.data.model.DocumentItemEntity
import com.example.data.model.SearchItemsEntity
import com.example.remote.RemoteMapper
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosResponse(
    @SerialName("meta")
    val meta: MetaResponse,
    @SerialName("documents")
    val documents: List<VideoDocumentsResponse>
) : RemoteMapper<SearchItemsEntity> {
    override fun toData(): SearchItemsEntity =
        SearchItemsEntity(
            isEnd = meta.isEnd,
            items = documents.map {
                DocumentItemEntity(
                    url = it.thumbnail,
                    dateTime = it.dateTime.toLocalDateTime(TimeZone.currentSystemDefault()),
                    bookMark = false
                )
            }
        )
}