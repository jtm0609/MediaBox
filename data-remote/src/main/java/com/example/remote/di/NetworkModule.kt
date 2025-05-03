package com.example.remote.di

import com.example.remote.api.ApiClient
import com.example.remote.api.ApiInterface
import com.example.remote.api.createApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiInterface(): ApiInterface =
        createApiService(ApiClient.BASE_URL)
}