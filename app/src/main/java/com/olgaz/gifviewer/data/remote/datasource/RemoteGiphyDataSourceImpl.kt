package com.olgaz.gifviewer.data.remote.datasource

import android.util.Log
import com.olgaz.gifviewer.data.datasource.GiphyDataSource
import com.olgaz.gifviewer.data.mapper.toGifImages
import com.olgaz.gifviewer.data.remote.BaseResult
import com.olgaz.gifviewer.data.remote.GiphyApiService
import com.olgaz.gifviewer.data.remote.models.DataDto
import com.olgaz.gifviewer.domain.model.GifImage
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import java.lang.Exception

class RemoteGiphyDataSourceImpl @Inject constructor(
    private val apiService: GiphyApiService
) : GiphyDataSource {

    override suspend fun searchImages(
        query: String,
        limit: Int,
        offset: Int
    ): BaseResult<List<GifImage>> {
        return getImages(query = query, limit = limit, offset = offset)
    }

    override suspend fun trendingImages(
        limit: Int,
        offset: Int
    ): BaseResult<List<GifImage>> {
        return getImages(limit = limit, offset = offset)
    }

    private suspend fun getImages(
        query: String? = null,
        limit: Int,
        offset: Int
    ): BaseResult<List<GifImage>> {
        try {
            val response = if (query.isNullOrEmpty()) {
                apiService.trendingImages(limit = limit, offset = offset)
            } else {
                apiService.searchImages(query = query, limit = limit, offset = offset)
            }
            val statusCode = response.meta.status
            val message = response.meta.msg
            if (statusCode == STATUS_OK) {
                val data: List<DataDto>? = response.responseData
                return BaseResult.Success(data?.toGifImages() ?: listOf())
            }
            return error("$statusCode $message")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Log.e("BaseResult Error", e.stackTraceToString())
            return error(e.message ?: e.toString())
        }
    }

    private fun <T : Any> error(errorMessage: String): BaseResult<T> =
        BaseResult.Error("Api call failed $errorMessage")

    companion object {
        private const val STATUS_OK = 200
    }
}