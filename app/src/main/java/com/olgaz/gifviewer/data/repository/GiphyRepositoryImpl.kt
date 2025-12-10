package com.olgaz.gifviewer.data.repository

import com.olgaz.gifviewer.data.datasource.GiphyDataSource
import com.olgaz.gifviewer.data.remote.BaseResult
import com.olgaz.gifviewer.domain.model.GifImage
import com.olgaz.gifviewer.domain.repository.GiphyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GiphyRepositoryImpl @Inject constructor(
    private val dataSource: GiphyDataSource
) : GiphyRepository {

    override suspend fun trendingImages(
        limit: Int?,
        offset: Int?
    ): Flow<BaseResult<List<GifImage>>> {
        return flow {
            emit(dataSource.trendingImages(limit = limit ?: 20, offset = offset ?: 0))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun searchImages(
        query: String,
        limit: Int?,
        offset: Int?
    ): Flow<BaseResult<List<GifImage>>> {
        return flow {
            emit(dataSource.searchImages(query = query, limit = limit ?: 20, offset = offset ?: 0))
        }.flowOn(Dispatchers.IO)
    }
}