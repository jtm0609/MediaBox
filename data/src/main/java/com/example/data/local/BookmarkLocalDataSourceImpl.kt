package com.example.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class BookmarkLocalDataSourceImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : BookmarkLocalDataSource {

    override suspend fun saveBookmarkItem(item: SearchLocal) {
        val list = getBookmarks().first().toMutableList()
        list.add(item)
        dataStoreManager.saveObjectList<SearchLocal>(BOOKMARK_KEY, list).invoke()
    }

    override suspend fun removeBookmarkItem(item: SearchLocal): Boolean {
        val list = getBookmarks().first().toMutableList()
        val index = list.indexOfFirst { it.url == item.url }
        if (index == -1) return false

        list.removeAt(index)
        dataStoreManager.saveObjectList<SearchLocal>(BOOKMARK_KEY, list).invoke()
        return true
    }

    override fun getBookmarks(): Flow<List<SearchLocal>> =
        dataStoreManager.getObjectListFlow<SearchLocal>(BOOKMARK_KEY)

    override suspend fun isBookmarked(url: String): Boolean =
        getBookmarks().first().any { it.url == url }

    companion object {
        const val BOOKMARK_KEY = "BOOKMARK_KEY"
    }
}