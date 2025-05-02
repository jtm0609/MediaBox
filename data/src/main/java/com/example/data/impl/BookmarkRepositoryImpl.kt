package com.example.data.impl

import com.example.data.local.BookmarkLocalDataSource
import com.example.data.local.toLocal
import com.example.domain.model.SearchItem
import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : BookmarkRepository {

    override suspend fun getBookmarks(): Result<List<SearchItem>> = runCatching {
        bookmarkLocalDataSource.getBookmarks().map { it.toDomain() }
    }

    override suspend fun saveBookmark(searchItem: SearchItem) {
        bookmarkLocalDataSource.saveBookmarkItem(searchItem.toLocal())
    }

    override suspend fun removeBookmark(searchItem: SearchItem): Result<Boolean> = runCatching {
        bookmarkLocalDataSource.removeBookmarkItem(searchItem.toLocal())
    }
}