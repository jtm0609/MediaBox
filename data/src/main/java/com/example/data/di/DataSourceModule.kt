package com.example.data.di

import com.example.data.api.ApiInterface
import com.example.data.paging.RemoteSearchResultPagingSource
import com.example.data.paging.RemoteSearchResultPagingSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiInterface: ApiInterface): RemoteSearchResultPagingSource =
        RemoteSearchResultPagingSourceImpl(
            apiInterface = apiInterface
        )
} 