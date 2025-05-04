package com.example.data.datasource

import com.example.data.model.SearchResultEntity

interface SearchRemoteDataSource {

    suspend fun getImageSearchResult(
        query: String, page: Int, size: Int, sort: String
    ): SearchResultEntity

    suspend fun getVideoSearchResult(
        query: String, page: Int, size: Int, sort: String
    ): SearchResultEntity
}