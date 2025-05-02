package com.example.data.local

import kotlinx.coroutines.flow.Flow

interface BookmarkLocalDataSource {

    suspend fun saveBookmarkItem(item: SearchLocal)
    suspend fun removeBookmarkItem(item: SearchLocal): Boolean
    fun getBookmarks(): Flow<List<SearchLocal>>
    suspend fun isBookmarked(url: String): Boolean
}