package com.olgaz.gifviewer.data.remote

import androidx.annotation.IntRange
import com.olgaz.gifviewer.data.remote.models.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [API Documentation](https://developers.giphy.com/docs/api)
 */
interface GiphyApiService {

    /**
     * API details [here](https://developers.giphy.com/docs/api/endpoint/#trending)
     */
    @GET("trending")
    suspend fun trendingImages(
        @Query("limit") limit: Int = 20,
        @Query("offset") @IntRange(from = 0,to = 499) offset: Int = 0,
        @Query("rating") rating: String = "g"
    ): BaseResponse

    /**
     * API details [here](https://developers.giphy.com/docs/api/endpoint/#search)
     *
     * rating - [g, pg, pg-13, r]
     */
    @GET("search")
    suspend fun searchImages(
        @Query("q") query: String? = null,
        @Query("limit") limit: Int = 20,
        @Query("offset") @IntRange(from = 0,to = 499) offset: Int = 0,
        @Query("rating") rating: String = "g"
    ): BaseResponse

}