package zaloznaya.olga.app.gifviewer.data.datasource

import kotlinx.coroutines.flow.Flow
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

interface IDeviceGiphyDataSource {
    suspend fun saveGifImages(images: List<GifImage>)
    fun getGifImages(): Flow<List<GifImage>>
    suspend fun getDeletedImagesIds(): List<String>
    suspend fun markAsDeleted(id: String)
}