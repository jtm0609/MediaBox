package com.example.domain.repository

import com.example.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun getBookmarks(): Flow<List<Bookmark>>
    suspend fun saveBookmark(bookmark: Bookmark)
    suspend fun removeBookmark(bookmark: Bookmark): Result<Boolean>
}