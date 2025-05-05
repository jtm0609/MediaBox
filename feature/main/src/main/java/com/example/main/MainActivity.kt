package com.example.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.theme.KnowmerceAssignmentTheme
import com.example.main.component.MainNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainNavigator = MainNavigator(rememberNavController())

            KnowmerceAssignmentTheme {
                MainScreen(
                    mainNavigator = mainNavigator
                )
            }
        }
    }
}