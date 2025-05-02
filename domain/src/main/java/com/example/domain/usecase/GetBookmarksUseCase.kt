package com.example.domain.usecase

import com.example.domain.model.SearchItem
import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) {

    suspend operator fun invoke(): Result<List<SearchItem>> =
        bookmarkRepository.getBookmarks()
}

