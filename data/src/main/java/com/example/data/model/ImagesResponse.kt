package com.example.data.model

import com.example.data.DataMapper
import com.example.domain.model.SearchItem
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    @SerialName("meta")
    val meta: MetaResponse,
    @SerialName("documents")
    val documents: List<ImageDocumentsResponse>
) : DataMapper<List<SearchItem>> {
    override fun toDomain(): List<SearchItem> =
        documents.map {
            SearchItem(
                url = it.thumbnailUrl,
                dateTime = it.dateTime.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
            )
        }
}

