package com.example.data.datasource

import com.example.data.model.SearchResultEntity

interface SearchRemoteDataSource {

    suspend fun getImageSearchResult(
        keyword: String, page: Int, size: Int, sort: String
    ): SearchResultEntity

    suspend fun getVideoSearchResult(
        keyword: String, page: Int, size: Int, sort: String
    ): SearchResultEntity
}