package zaloznaya.olga.app.gifviewer.data.remote.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import zaloznaya.olga.app.gifviewer.data.remote.models.BaseResponse

interface GiphyApiService {

    @GET("trending")
    suspend fun trendingImages(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): BaseResponse

    @GET("search")
    suspend fun searchImages(
        @Query("q") query: String? = null,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): BaseResponse

}