package com.example.remote.impl

import com.example.data.datasource.SearchRemoteDataSource
import com.example.data.model.SearchResultEntity
import com.example.remote.api.ApiInterface
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface
): SearchRemoteDataSource {

    override suspend fun getImageSearchResult(query: String, page: Int, size: Int, sort: String): SearchResultEntity {
        return apiInterface.getImages(
            query = query,
            page = page,
            size = size,
            sort = sort
        ).toData()
    }

    override suspend fun getVideoSearchResult(query: String, page: Int, size: Int, sort: String): SearchResultEntity {
        return apiInterface.getVideos(
            query = query,
            page = page,
            size = size,
            sort = sort
        ).toData()
    }
}