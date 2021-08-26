package zaloznaya.olga.app.gifviewer.data.device.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import zaloznaya.olga.app.gifviewer.data.datasource.IDeviceGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.device.db.dao.ImagesDao
import zaloznaya.olga.app.gifviewer.data.device.db.entity.GifImageEntity
import zaloznaya.olga.app.gifviewer.data.mappers.IMapper
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

class DeviceGiphyDataSource (
    private val dao: ImagesDao,
    private val mapperFromEntity: IMapper<GifImageEntity, GifImage>,
    private val mapperToEntity: IMapper<GifImage, GifImageEntity>
) : IDeviceGiphyDataSource {

    override suspend fun saveGifImages(images: List<GifImage>) =
        dao.saveGifImages(images = images.map(mapperToEntity::mapFrom))

    override fun getGifImages(): Flow<List<GifImage>> =
        dao.getGifImages().map {
            it.map (mapperFromEntity::mapFrom)
        }

    override suspend fun getDeletedImagesIds(): List<String> = dao.getDeletedImagesIds()

    override suspend fun markAsDeleted(id: String) = dao.markAsDeleted(id)
}