package com.example.data.di

import com.example.data.impl.BookmarkRepositoryImpl
import com.example.data.impl.SearchRepositoryImpl
import com.example.domain.repository.BookmarkRepository
import com.example.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSearchRepository(repositoryImpl: SearchRepositoryImpl): SearchRepository

    @Singleton
    @Binds
    abstract fun bindBookmarkRepository(bookmarkRepositoryImpl: BookmarkRepositoryImpl): BookmarkRepository
} 