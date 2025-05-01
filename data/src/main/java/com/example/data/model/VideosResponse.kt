package com.example.data.model

import com.example.data.DataMapper
import com.example.domain.model.SearchItem
import com.example.domain.model.SearchItemType
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosResponse(
    @SerialName("meta")
    val meta: MetaResponse,
    @SerialName("documents")
    val documents: List<VideoDocumentsResponse>
) : DataMapper<List<SearchItem>> {
    override fun toDomain(): List<SearchItem> =
        documents.map {
            SearchItem(
                url = it.thumbnail,
                dateTime = it.dateTime.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime(),
                type = SearchItemType.VIDEO
            )
        }
}