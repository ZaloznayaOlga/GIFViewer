package com.olgaz.gifviewer.di

import com.olgaz.gifviewer.data.datasource.GiphyDataSource
import com.olgaz.gifviewer.data.remote.GiphyApiService
import com.olgaz.gifviewer.data.remote.datasource.RemoteGiphyDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteGiphyDataSource(
        apiService: GiphyApiService
    ) : GiphyDataSource = RemoteGiphyDataSourceImpl(apiService)
}