package com.olgaz.gifviewer.domain.repository

import com.olgaz.gifviewer.data.remote.BaseResult
import com.olgaz.gifviewer.domain.model.GifImage
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {
    suspend fun trendingImages(limit: Int? = null, offset: Int? = null): Flow<BaseResult<List<GifImage>>>
    suspend fun searchImages(query: String, limit: Int? = null, offset: Int? = null): Flow<BaseResult<List<GifImage>>>
}