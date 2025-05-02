package com.example.data.local

import javax.inject.Inject

class BookmarkLocalDataSourceImpl @Inject constructor(
    private val sharedPreferenceManager: SharedPreferenceManager
) : BookmarkLocalDataSource {

    override fun saveBookmarkItem(item: SearchLocal) {
        val list = getBookmarks().toMutableList()
        list.add(item)
        sharedPreferenceManager.saveObjectList(BOOKMARK_KEY, list)
    }

    override fun removeBookmarkItem(item: SearchLocal): Boolean {
        val list = getBookmarks().toMutableList()
        val index = list.indexOfFirst { it.url == item.url }
        if (index == -1) return false
        
        list.removeAt(index)
        sharedPreferenceManager.saveObjectList(BOOKMARK_KEY, list)
        return true
    }

    override fun getBookmarks(): List<SearchLocal> =
        sharedPreferenceManager.getObjectList(BOOKMARK_KEY)

    override fun isBookmarked(url: String): Boolean =
        getBookmarks().any { it.url == url }

    companion object {
        const val BOOKMARK_KEY = "BOOKMARK_KEY"
    }
}