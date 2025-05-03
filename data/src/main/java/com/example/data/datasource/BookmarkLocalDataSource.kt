package com.example.data.datasource

import com.example.data.model.DocumentItemEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkLocalDataSource {

    fun getBookmarks(): Flow<List<DocumentItemEntity>>
    suspend fun saveBookmarkItem(item: DocumentItemEntity)
    suspend fun removeBookmarkItem(item: DocumentItemEntity): Boolean
    suspend fun isBookmarked(url: String): Boolean
}