package com.example.presentation.feature.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.presentation.navigation.NavRoute

fun NavController.navigateSearch(navOptions: NavOptions) {
    this.navigate(NavRoute.Search, navOptions)
}

fun NavGraphBuilder.searchNavGraph(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onHideKeyboard: () -> Unit
) {

    composable<NavRoute.Search> {
        SearchScreen(
            padding = padding,
            onShowErrorSnackBar = onShowErrorSnackBar,
            onHideKeyboard = onHideKeyboard
        )
    }
}