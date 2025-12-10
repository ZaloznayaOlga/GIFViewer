package com.olgaz.gifviewer.di

import com.olgaz.gifviewer.BuildConfig
import com.olgaz.gifviewer.data.remote.GiphyApiService
import com.olgaz.gifviewer.data.remote.interceptor.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.giphy.com/v1/gifs/"

    @Provides
    @Singleton
    fun provideJson() = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideConverterFactory(
        json: Json
    ): Converter.Factory = json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey = BuildConfig.GIPHY_API_KEY))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    internal fun provideGiphyApiService(
        retrofit: Retrofit
    ): GiphyApiService = retrofit.create(GiphyApiService::class.java)
}