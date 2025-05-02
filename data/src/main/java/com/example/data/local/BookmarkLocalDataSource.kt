package com.example.data.local

interface BookmarkLocalDataSource {

    fun saveBookmarkItem(item: SearchLocal)
    fun removeBookmarkItem(item: SearchLocal): Boolean
    fun getBookmarks(): List<SearchLocal>
    fun isBookmarked(url:String): Boolean
}