package com.example.data.datasource

import com.example.data.model.BookmarkEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkLocalDataSource {

    fun getBookmarks(): Flow<List<BookmarkEntity>>
    suspend fun saveBookmark(item: BookmarkEntity)
    suspend fun removeBookmark(item: BookmarkEntity): Boolean
    suspend fun isBookmarked(url: String): Boolean
}