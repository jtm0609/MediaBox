package com.example.presentation.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.navigation.NavRoute

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {

    composable<NavRoute.Home> {
        HomeScreen(
            padding = padding,
            onShowErrorSnackBar = onShowErrorSnackBar
        )
    }
}