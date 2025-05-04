package com.example.remote.model

import com.example.data.model.SearchItemEntity
import com.example.data.model.SearchResultEntity
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
) : RemoteMapper<SearchResultEntity> {

    override fun toData(): SearchResultEntity =
        SearchResultEntity(
            isEnd = meta.isEnd,
            items = documents.map {
                SearchItemEntity(
                    url = it.thumbnail,
                    dateTime = it.dateTime.toLocalDateTime(TimeZone.currentSystemDefault()),
                    isBookMark = false
                )
            }
        )
}