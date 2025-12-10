package com.olgaz.gifviewer.di

import com.olgaz.gifviewer.domain.repository.GiphyRepository
import com.olgaz.gifviewer.domain.usecase.GetTrendingImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTrendingImagesUseCase(repository: GiphyRepository) =
        GetTrendingImagesUseCase(repository)

}