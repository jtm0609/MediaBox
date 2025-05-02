package com.example.presentation.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.presentation.navigation.NavRoute

fun NavController.navigateSearch(navOptions: NavOptions) {
    this.navigate(NavRoute.Search, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {

    composable<NavRoute.Search> {
        HomeScreen(
            padding = padding,
            onShowErrorSnackBar = onShowErrorSnackBar
        )
    }
}