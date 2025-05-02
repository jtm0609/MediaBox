package com.example.presentation.navigation

import androidx.navigation.NavHostController
import com.example.presentation.feature.bookmark.navigateBookmark

class Navigator(
    val navController: NavHostController
) {
    val startDestination = NavRoute.Home

    fun navigateBookMark() {
        navController.navigateBookmark()
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}