package com.example.presentation.navigation

import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable
    data object Search : NavRoute()

    @Serializable
    data object BookMark : NavRoute()
}