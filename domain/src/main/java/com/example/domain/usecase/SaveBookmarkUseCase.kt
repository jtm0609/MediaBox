package com.example.domain.usecase

import com.example.domain.model.Bookmark
import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class SaveBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) {

    suspend operator fun invoke(bookmark: Bookmark) =
        bookmarkRepository.saveBookmark(bookmark)
}

