package com.example.local.impl

import com.example.data.datasource.BookmarkLocalDataSource
import com.example.data.model.BookmarkEntity
import com.example.local.datastroe.DataStoreManager
import com.example.local.model.BookmarkLocal
import com.example.local.model.toLocal
import com.example.local.toData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkLocalDataSourceImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : BookmarkLocalDataSource {

    override suspend fun saveBookmark(item: BookmarkEntity) {
        val currentBookmarks = dataStoreManager.getObjectListFlow<BookmarkLocal>(BOOKMARK_KEY).first()
        val updatedBookmarks = currentBookmarks.toMutableList().apply {
            add(item.toLocal())
        }
        dataStoreManager.putObjectList(BOOKMARK_KEY, updatedBookmarks)
    }

    override suspend fun removeBookmark(item: BookmarkEntity): Boolean {
        val currentBookmarks = dataStoreManager.getObjectListFlow<BookmarkLocal>(BOOKMARK_KEY).first()
        val index = currentBookmarks.indexOfFirst { it.url == item.url }
        if (index == -1) return false
        val updatedBookmarks = currentBookmarks.toMutableList().apply {
            removeAt(index)
        }
        dataStoreManager.putObjectList(BOOKMARK_KEY, updatedBookmarks)
        return true
    }

    override fun getBookmarks(): Flow<List<BookmarkEntity>> =
        dataStoreManager.getObjectListFlow<BookmarkLocal>(BOOKMARK_KEY).map { it.toData() }

    override suspend fun isBookmarked(url: String): Boolean =
        dataStoreManager.getObjectListFlow<BookmarkLocal>(BOOKMARK_KEY).first()
            .any { it.url == url }

    companion object {
        const val BOOKMARK_KEY = "BOOKMARK_KEY"
    }
}