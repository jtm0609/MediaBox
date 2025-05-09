package com.example.remote.impl

import com.example.data.datasource.SearchRemoteDataSource
import com.example.data.model.SearchResultEntity
import com.example.remote.api.ApiInterface
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface
): SearchRemoteDataSource {

    override suspend fun getImageSearchResult(keyword: String, page: Int, size: Int, sort: String): SearchResultEntity {
        return apiInterface.getImages(
            keyword = keyword,
            page = page,
            size = size,
            sort = sort
        ).toData()
    }

    override suspend fun getVideoSearchResult(keyword: String, page: Int, size: Int, sort: String): SearchResultEntity {
        return apiInterface.getVideos(
            keyword = keyword,
            page = page,
            size = size,
            sort = sort
        ).toData()
    }
}