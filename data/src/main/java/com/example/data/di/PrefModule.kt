package com.example.data.di

import android.content.Context
import com.example.data.datasource.SearchRemoteDataSource
import com.example.data.datasource.SearchRemoteDataSourceImpl
import com.example.data.local.SharedPreferenceManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(
        @ApplicationContext context: Context
    ): SharedPreferenceManager {
        return SharedPreferenceManager(context)
    }
} 