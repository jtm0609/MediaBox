package com.example.presentation.navigation

import androidx.navigation.NavHostController
import com.example.presentation.feature.bookmark.navigateRouteDetail

class Navigator(
    val navController: NavHostController
) {
    val startDestination = NavRoute.Home

    fun navigateBookMark() {
        navController.navigateRouteDetail()
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}