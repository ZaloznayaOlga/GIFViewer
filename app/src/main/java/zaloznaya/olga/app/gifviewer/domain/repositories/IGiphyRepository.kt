package zaloznaya.olga.app.gifviewer.domain.repositories

import kotlinx.coroutines.flow.Flow
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

interface IGiphyRepository {
    suspend fun getImages(limit: Int, offset: Int): List<GifImage>?
    suspend fun searchImages(query: String, limit: Int, offset: Int): List<GifImage>?
    suspend fun saveGifImages(images: List<GifImage>)
    fun getGifImagesFromDb(): Flow<List<GifImage>>
    suspend fun getDeletedImagesIds(): List<String>
    suspend fun markAsDeleted(id: String)
}