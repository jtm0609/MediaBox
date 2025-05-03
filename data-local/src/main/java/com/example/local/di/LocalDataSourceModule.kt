package com.example.local.di

import com.example.data.datasource.BookmarkLocalDataSource
import com.example.local.impl.BookmarkLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    
    @Singleton
    @Binds
    abstract fun bindBookmarkLocalDataSource(bookmarkLocalDataSourceImpl: BookmarkLocalDataSourceImpl): BookmarkLocalDataSource
} 