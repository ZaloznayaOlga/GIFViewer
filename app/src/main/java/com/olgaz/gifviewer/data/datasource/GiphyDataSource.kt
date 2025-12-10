package com.olgaz.gifviewer.data.datasource

import com.olgaz.gifviewer.data.remote.BaseResult
import com.olgaz.gifviewer.domain.model.GifImage

interface GiphyDataSource {
    suspend fun searchImages(query: String, limit: Int, offset: Int): BaseResult<List<GifImage>>
    suspend fun trendingImages(limit: Int, offset: Int): BaseResult<List<GifImage>>
}