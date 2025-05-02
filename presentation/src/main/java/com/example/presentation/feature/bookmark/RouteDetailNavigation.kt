package com.example.presentation.feature.bookmark

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.navigation.NavRoute

fun NavController.navigateBookmark() {
    this.navigate(NavRoute.BookMark)
}

fun NavGraphBuilder.bookmarkNavGraph(onShowErrorSnackBar: (throwable: Throwable?) -> Unit) {

    composable<NavRoute.BookMark> { navBackStackEntry ->
        BookmarkScreen (
            onShowErrorSnackBar = onShowErrorSnackBar
        )
    }
}
