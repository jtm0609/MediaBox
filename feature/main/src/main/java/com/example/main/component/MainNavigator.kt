package com.example.main.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.example.bookmark.navigation.navigateBookmark
import com.example.core_ui.navigation.NavRoute
import com.example.main.MainTab
import com.example.search.navigation.navigateSearch

class MainNavigator(
    val navController: NavHostController
) {
    val startDestination = NavRoute.Search

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.Search -> navController.navigateSearch(navOptions)
            MainTab.BOOKMARK -> navController.navigateBookmark(navOptions)
        }
    }

    @Composable
    fun shouldShowBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}