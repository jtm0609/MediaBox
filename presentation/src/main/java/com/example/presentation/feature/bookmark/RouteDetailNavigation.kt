package com.example.presentation.feature.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.presentation.feature.bookmark.composable.BookmarkScreen
import com.example.presentation.navigation.NavRoute

fun NavController.navigateBookmark(navOptions: NavOptions) {
    this.navigate(NavRoute.BookMark, navOptions)
}

fun NavGraphBuilder.bookmarkNavGraph(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {
    composable<NavRoute.BookMark> {
        BookmarkScreen(
            padding = padding,
            onShowErrorSnackBar = onShowErrorSnackBar
        )
    }
}
