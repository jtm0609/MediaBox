package com.example.data.impl

import com.example.data.datasource.BookmarkLocalDataSource
import com.example.data.model.toData
import com.example.data.toDomain
import com.example.domain.model.Bookmark
import com.example.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : BookmarkRepository {

    override suspend fun getBookmarks(): Flow<List<Bookmark>> =
        bookmarkLocalDataSource.getBookmarks().map { bookmarks ->
            bookmarks.toDomain()
        }

    override suspend fun saveBookmark(bookmark: Bookmark) {
        bookmarkLocalDataSource.saveBookmark(bookmark.toData())
    }

    override suspend fun removeBookmark(bookmark: Bookmark): Result<Boolean> = runCatching {
        bookmarkLocalDataSource.removeBookmark(bookmark.toData())
    }
}