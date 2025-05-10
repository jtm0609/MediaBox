package com.example.bookmark

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.bookmark.model.BookmarkModel
import kotlinx.collections.immutable.persistentListOf

class BookmarkPreviewProvider : PreviewParameterProvider<BookmarkContract.State> {
    override val values = sequenceOf(
        BookmarkContract.State.Loading,
        BookmarkContract.State.Idle,
        BookmarkContract.State.Success(
            persistentListOf(
                BookmarkModel("https://search4.kakaocdn.net/argon/138x78_80_pr/E1H7Out9GDz"),
                BookmarkModel("https://search1.kakaocdn.net/argon/138x78_80_pr/GrZsTm9zhou"),
                BookmarkModel("https://search3.kakaocdn.net/argon/138x78_80_pr/AjJLoc9Cv77")
            )
        )
    )
}