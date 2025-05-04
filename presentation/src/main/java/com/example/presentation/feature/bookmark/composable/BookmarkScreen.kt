package com.example.presentation.feature.bookmark.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.common.composable.Progress
import com.example.presentation.feature.bookmark.BookmarkContract
import com.example.presentation.feature.bookmark.BookmarkPreviewProvider
import com.example.presentation.feature.bookmark.BookmarkViewModel

@Composable
fun BookmarkScreen(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    val state by bookmarkViewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = bookmarkViewModel.effect

    LaunchedEffect(effectFlow) {
        effectFlow.collect { effect ->
            when (effect) {
                is BookmarkContract.Effect.ShowError -> {
                    onShowErrorSnackBar(effect.throwable)
                }
            }
        }
    }

    BookmarkScreenContent(
        state = state,
        padding = padding,
        onShowErrorSnackBar = onShowErrorSnackBar
    )
}

@Composable
private fun BookmarkScreenContent(
    state: BookmarkContract.State,
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        when (state) {
            is BookmarkContract.State.Idle -> EmptyBookmarksGuide()
            is BookmarkContract.State.Loading -> Progress()
            is BookmarkContract.State.Success -> BookmarkGrid(bookmarks = state.bookmarkList)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkScreenPreview(
    @PreviewParameter(BookmarkPreviewProvider::class) state: BookmarkContract.State
) {
    BookmarkScreenContent(
        state = state,
        padding = PaddingValues(),
        onShowErrorSnackBar = {}
    )
}