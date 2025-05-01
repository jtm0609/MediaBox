package com.example.data.datasource

import android.provider.MediaStore.Video
import com.example.data.model.ImagesResponse
import com.example.data.model.VideosResponse

interface SearchRemoteDataSource {

    suspend fun getImageSearchResult(
        query: String, page: Int, size: Int, sort: String
    ): ImagesResponse

    suspend fun getVideoSearchResult(
        query: String, page: Int, size: Int, sort: String
    ): VideosResponse
}