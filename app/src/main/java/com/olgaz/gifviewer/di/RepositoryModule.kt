package com.olgaz.gifviewer.di

import com.olgaz.gifviewer.data.datasource.GiphyDataSource
import com.olgaz.gifviewer.data.repository.GiphyRepositoryImpl
import com.olgaz.gifviewer.domain.repository.GiphyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideGiphyRepository(
        dataSource: GiphyDataSource
    ): GiphyRepository = GiphyRepositoryImpl(dataSource)
}