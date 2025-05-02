package com.example.domain.repository

import com.example.domain.model.SearchItem

interface BookmarkRepository {

    suspend fun getBookmarks(): Result<List<SearchItem>>

    suspend fun saveBookmark(searchItem: SearchItem)

    suspend fun removeBookmark(searchItem: SearchItem): Result<Boolean>
}