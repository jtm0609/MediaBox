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
        val list = getBookmarks().first().map { it.toLocal() }.toMutableList()
        list.add(item.toLocal())
        dataStoreManager.putObjectList<SearchLocal>(BOOKMARK_KEY, list).invoke()
    }

    override suspend fun removeBookmarkItem(item: BookmarkItemEntity): Boolean {
        val list = getBookmarks().first().map { it.toLocal() }.toMutableList()
        val index = list.indexOfFirst { it.url == item.url }
        if (index == -1) return false

        list.removeAt(index)
        dataStoreManager.putObjectList<SearchLocal>(BOOKMARK_KEY, list).invoke()
        return true
    }

    override fun getBookmarks(): Flow<List<BookmarkItemEntity>> =
        dataStoreManager.getObjectListFlow<SearchLocal>(BOOKMARK_KEY).map { it.toData() }

    override suspend fun isBookmarked(url: String): Boolean =
        getBookmarks().first().any { it.url == url }

    companion object {
        const val BOOKMARK_KEY = "BOOKMARK_KEY"
    }
}