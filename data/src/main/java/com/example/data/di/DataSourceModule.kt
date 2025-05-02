package com.example.data.di

import com.example.data.datasource.SearchRemoteDataSource
import com.example.data.datasource.SearchRemoteDataSourceImpl
import com.example.data.local.BookmarkLocalDataSource
import com.example.data.local.BookmarkLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindRemoteDataSource(searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl): SearchRemoteDataSource
    
    @Singleton
    @Binds
    abstract fun bindBookmarkLocalDataSource(bookmarkLocalDataSourceImpl: BookmarkLocalDataSourceImpl): BookmarkLocalDataSource
} 