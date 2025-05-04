package com.example.domain.repository

import com.example.domain.model.BookmarkItem
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun getBookmarks(): Flow<List<BookmarkItem>>
    suspend fun saveBookmark(bookmarkItem: BookmarkItem)
    suspend fun removeBookmark(bookmarkItem: BookmarkItem): Result<Boolean>
}