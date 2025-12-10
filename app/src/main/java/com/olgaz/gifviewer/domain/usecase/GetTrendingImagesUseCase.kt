package com.olgaz.gifviewer.domain.usecase

import com.olgaz.gifviewer.data.remote.BaseResult
import com.olgaz.gifviewer.domain.model.GifImage
import com.olgaz.gifviewer.domain.repository.GiphyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingImagesUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<GifImage>>> {
        return repository.trendingImages()
    }
}