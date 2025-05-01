package com.example.data.datasource

import com.example.data.api.ApiInterface
import com.example.data.model.ImagesResponse
import com.example.data.model.VideosResponse
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface
): SearchRemoteDataSource {

    override suspend fun getImageSearchResult(query: String, page: Int, size: Int, sort: String): ImagesResponse {
        return apiInterface.getImages(
            query = query,
            page = page,
            size = size,
            sort = sort
        )
    }

    override suspend fun getVideoSearchResult(query: String, page: Int, size: Int, sort: String): VideosResponse {
        return apiInterface.getVideos(
            query = query,
            page = page,
            size = size,
            sort = sort
        )
    }
}