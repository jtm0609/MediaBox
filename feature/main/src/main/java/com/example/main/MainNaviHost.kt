package com.example.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.bookmark.navigation.bookmarkNavGraph
import com.example.main.component.MainNavigator
import com.example.search.navigation.searchNavGraph

@Composable
fun MainNaviHost(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    mainNavigator: MainNavigator,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onHideKeyboard: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
    ) {
        NavHost(
            navController = mainNavigator.navController,
            startDestination = mainNavigator.startDestination,
        ) {
            searchNavGraph(
                padding = padding,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onHideKeyboard = onHideKeyboard
            )
            bookmarkNavGraph(
                padding = padding,
                onShowErrorSnackBar = onShowErrorSnackBar
            )
        }
    }
}