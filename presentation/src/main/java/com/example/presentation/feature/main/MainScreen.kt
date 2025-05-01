package com.example.presentation.feature.main

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.presentation.R
import com.example.presentation.navigation.Navigator
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@Composable
fun MainScreen(
    navigator: Navigator
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val localContextResource = LocalContext.current.resources
    val onShowErrorSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                when (throwable) {
                    is UnknownHostException -> localContextResource.getString(R.string.error_message_network)
                    else -> localContextResource.getString(R.string.error_message_unknown)
                }
            )
        }
    }

    MainScreenContent(
        navigator = navigator,
        onShowErrorSnackBar = onShowErrorSnackBar,
        snackBarHostState = snackBarHostState
    )
}

@Composable
private fun MainScreenContent(
    navigator: Navigator,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Scaffold(
        content = { paddingValue ->
            MainNaviHost(
                padding = paddingValue,
                navigator = navigator,
                onShowErrorSnackBar = onShowErrorSnackBar
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    )
}