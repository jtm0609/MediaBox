package com.example.data.di

import com.example.data.api.ApiClient
import com.example.data.api.ApiInterface
import com.example.data.api.createApiService
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