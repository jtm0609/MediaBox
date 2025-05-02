package com.example.domain.repository

import com.example.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun getBookmarks(): Flow<List<SearchItem>>

    suspend fun saveBookmark(searchItem: SearchItem)

    suspend fun removeBookmark(searchItem: SearchItem): Result<Boolean>
}