package com.example.data.datasource

import com.example.data.model.BookmarkItemEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkLocalDataSource {

    fun getBookmarks(): Flow<List<BookmarkItemEntity>>
    suspend fun saveBookmarkItem(item: BookmarkItemEntity)
    suspend fun removeBookmarkItem(item: BookmarkItemEntity): Boolean
    suspend fun isBookmarked(url: String): Boolean
}