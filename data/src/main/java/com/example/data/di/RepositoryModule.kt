package com.example.data.di

import com.example.data.impl.SearchRepositoryImpl
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
    abstract fun bindRouteRepository(repositoryImpl: SearchRepositoryImpl): SearchRepository
} 