package com.example.domain.usecase

import com.example.domain.model.BookmarkItem
import com.example.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) {

    suspend operator fun invoke(): Flow<List<BookmarkItem>> =
        bookmarkRepository.getBookmarks()
}

