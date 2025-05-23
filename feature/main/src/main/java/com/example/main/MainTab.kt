package com.example.main

import androidx.compose.runtime.Composable
import com.example.core_ui.navigation.NavRoute

enum class MainTab(
    val iconResId: Int,
    val contentDescription: String,
    val route: NavRoute,
) {

    Search(
        iconResId = R.drawable.ic_search,
        contentDescription = "검색",
        NavRoute.Search,
    ),
    BOOKMARK(
        iconResId = R.drawable.ic_bookmark,
        contentDescription = "북마크",
        NavRoute.BookMark,
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (NavRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (NavRoute) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
