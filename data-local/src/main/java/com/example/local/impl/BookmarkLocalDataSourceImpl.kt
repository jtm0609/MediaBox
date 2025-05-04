package com.example.local.impl

import com.example.data.datasource.BookmarkLocalDataSource
import com.example.data.model.BookmarkItemEntity
import com.example.local.datastroe.DataStoreManager
import com.example.local.model.SearchLocal
import com.example.local.model.toLocal
import com.example.local.toData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkLocalDataSourceImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : BookmarkLocalDataSource {

    override suspend fun saveBookmarkItem(item: BookmarkItemEntity) {
        val currentBookmarks = dataStoreManager.getObjectListFlow<SearchLocal>(BOOKMARK_KEY).first()
        val updatedBookmarks = currentBookmarks.toMutableList().apply {
            add(item.toLocal())
        }
        dataStoreManager.putObjectList(BOOKMARK_KEY, updatedBookmarks)
    }

    override suspend fun removeBookmarkItem(item: BookmarkItemEntity): Boolean {
        val currentBookmarks = dataStoreManager.getObjectListFlow<SearchLocal>(BOOKMARK_KEY).first()
        val index = currentBookmarks.indexOfFirst { it.url == item.url }
        if (index == -1) return false
        val updatedBookmarks = currentBookmarks.toMutableList().apply {
            removeAt(index)
        }
        dataStoreManager.putObjectList(BOOKMARK_KEY, updatedBookmarks)
        return true
    }

    override fun getBookmarks(): Flow<List<BookmarkItemEntity>> =
        dataStoreManager.getObjectListFlow<SearchLocal>(BOOKMARK_KEY).map { it.toData() }

    override suspend fun isBookmarked(url: String): Boolean =
        dataStoreManager.getObjectListFlow<SearchLocal>(BOOKMARK_KEY).first()
            .any { it.url == url }

    companion object {
        const val BOOKMARK_KEY = "BOOKMARK_KEY"
    }
}