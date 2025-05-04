package com.example.data.model

import com.example.domain.model.SearchResult

data class SearchResultCache(
    val searchResults: List<SearchResult>,
    val timestamp: Long
) {

    fun isExpired(expirationTimeMillis: Long): Boolean {
        return System.currentTimeMillis() - timestamp > expirationTimeMillis
    }
}