package com.example.data.impl

import com.example.data.datasource.BookmarkLocalDataSource
import com.example.data.model.toData
import com.example.domain.model.BookmarkItem
import com.example.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : BookmarkRepository {

    override suspend fun getBookmarks(): Flow<List<BookmarkItem>> =
        bookmarkLocalDataSource.getBookmarks().map { bookmarks ->
            bookmarks.map { it.toDomain() }
        }

    override suspend fun saveBookmark(bookmarkItem: BookmarkItem) {
        bookmarkLocalDataSource.saveBookmarkItem(bookmarkItem.toData())
    }

    override suspend fun removeBookmark(bookmarkItem: BookmarkItem): Result<Boolean> = runCatching {
        bookmarkLocalDataSource.removeBookmarkItem(bookmarkItem.toData())
    }
}