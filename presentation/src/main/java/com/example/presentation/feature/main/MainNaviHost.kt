package com.example.presentation.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.presentation.feature.bookmark.bookmarkNavGraph
import com.example.presentation.feature.home.homeNavGraph
import com.example.presentation.navigation.Navigator

@Composable
fun MainNaviHost(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    navigator: Navigator,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
        ) {
            homeNavGraph(
                padding = padding,
                onShowErrorSnackBar = onShowErrorSnackBar,
            )
            bookmarkNavGraph(
                padding = padding,
                onShowErrorSnackBar = onShowErrorSnackBar
            )
        }
    }
}