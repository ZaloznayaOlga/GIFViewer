package zaloznaya.olga.app.gifviewer.data.repositories

import kotlinx.coroutines.flow.Flow
import zaloznaya.olga.app.gifviewer.data.datasource.IDeviceGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.datasource.IRemoteGiphyDataSource
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import javax.inject.Inject

class GiphyRepository @Inject constructor(
    private val remoteSource: IRemoteGiphyDataSource,
    private val deviceDataSource: IDeviceGiphyDataSource
) : IGiphyRepository {

    override suspend fun getImages(limit: Int, offset: Int): List<GifImage>? =
        remoteSource.getImages(limit, offset)?.also { deviceDataSource.saveGifImages(it) }

    override suspend fun searchImages(query: String, limit: Int, offset: Int): List<GifImage>? =
        remoteSource.searchImages(query, limit, offset)?.also { deviceDataSource.saveGifImages(it) }

    override suspend fun saveGifImages(images: List<GifImage>) = deviceDataSource.saveGifImages(images)

    override fun getGifImagesFromDb(): Flow<List<GifImage>> = deviceDataSource.getGifImages()

    override suspend fun getDeletedImagesIds(): List<String> = deviceDataSource.getDeletedImagesIds()

    override suspend fun markAsDeleted(id: String) = deviceDataSource.markAsDeleted(id)
}