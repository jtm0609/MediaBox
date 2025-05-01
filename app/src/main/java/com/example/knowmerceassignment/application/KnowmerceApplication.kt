package com.example.knowmerceassignment.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KnowmerceApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}