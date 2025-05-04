package com.example.domain.usecase

import com.example.domain.model.BookmarkItem
import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) {

    suspend operator fun invoke(bookmarkItem: BookmarkItem): Result<Boolean> =
        bookmarkRepository.removeBookmark(bookmarkItem)
}

