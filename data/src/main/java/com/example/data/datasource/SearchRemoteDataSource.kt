package com.example.data.datasource

import com.example.data.model.SearchItemsEntity

interface SearchRemoteDataSource {

    suspend fun getImageSearchResult(
        query: String, page: Int, size: Int, sort: String
    ): SearchItemsEntity

    suspend fun getVideoSearchResult(
        query: String, page: Int, size: Int, sort: String
    ): SearchItemsEntity
}