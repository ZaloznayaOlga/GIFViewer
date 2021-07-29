package zaloznaya.olga.app.gifviewer.data.remote.datasource

import zaloznaya.olga.app.gifviewer.data.datasource.IRemoteGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.mappers.IMapper
import zaloznaya.olga.app.gifviewer.data.remote.models.DataDto
import zaloznaya.olga.app.gifviewer.data.remote.retrofit.GiphyApiService
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import javax.inject.Inject

class RemoteGiphyDataSource @Inject constructor(
    private val apiService: GiphyApiService,
    private val mapper: IMapper<List<DataDto>?, List<GifImage>>
) : IRemoteGiphyDataSource {
    override suspend fun getImages(limit: Int, offset: Int): List<GifImage> =
        mapper.mapFrom(apiService.trendingImages(limit, offset).responseData)

    override suspend fun searchImages(query: String, limit: Int, offset: Int): List<GifImage> =
        mapper.mapFrom(apiService.searchImages(query, limit, offset).responseData)
}