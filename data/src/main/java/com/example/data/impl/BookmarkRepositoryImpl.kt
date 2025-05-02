package com.example.data.impl

import com.example.data.local.BookmarkLocalDataSource
import com.example.data.local.toLocal
import com.example.domain.model.SearchItem
import com.example.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : BookmarkRepository {

    override suspend fun getBookmarks(): Flow<List<SearchItem>> =
        bookmarkLocalDataSource.getBookmarks().map { bookmarks ->
            bookmarks.map { it.toDomain() }
        }

    override suspend fun saveBookmark(searchItem: SearchItem) {
        bookmarkLocalDataSource.saveBookmarkItem(searchItem.toLocal())
    }

    override suspend fun removeBookmark(searchItem: SearchItem): Result<Boolean> = runCatching {
        bookmarkLocalDataSource.removeBookmarkItem(searchItem.toLocal())
    }
}