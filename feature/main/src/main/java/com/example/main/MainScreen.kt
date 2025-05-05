package com.example.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.main.component.MainBottomBar
import com.example.main.component.MainNavigator
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@Composable
fun MainScreen(
    mainNavigator: MainNavigator
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
    val keyboardController = LocalSoftwareKeyboardController.current
    val onHideKeyboard: () -> Unit = {
        keyboardController?.hide()
    }

    MainScreenContent(
        mainNavigator = mainNavigator,
        onShowErrorSnackBar = onShowErrorSnackBar,
        snackBarHostState = snackBarHostState,
        onHideKeyboard = onHideKeyboard
    )
}

@Composable
private fun MainScreenContent(
    mainNavigator: MainNavigator,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onHideKeyboard: () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Scaffold(
        content = { paddingValue ->
            MainNaviHost(
                padding = paddingValue,
                mainNavigator = mainNavigator,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onHideKeyboard = onHideKeyboard
            )
        },
        bottomBar = {
            MainBottomBar(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 8.dp, end = 8.dp, bottom = 28.dp),
                visible = mainNavigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toPersistentList(),
                currentTab = mainNavigator.currentTab,
                onTabSelected = { mainNavigator.navigate(it) }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    )
}