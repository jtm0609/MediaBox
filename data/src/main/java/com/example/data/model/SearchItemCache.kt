package com.example.data.model

import com.example.domain.model.SearchItem

data class SearchItemCache(
    val searchItems: List<SearchItem>,
    val timestamp: Long
) {
    fun isExpired(expirationTimeMillis: Long): Boolean {
        return System.currentTimeMillis() - timestamp > expirationTimeMillis
    }
}