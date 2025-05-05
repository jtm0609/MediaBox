package com.example.bookmark.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.bookmark.BookmarkScreen
import com.example.core_ui.navigation.NavRoute

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
