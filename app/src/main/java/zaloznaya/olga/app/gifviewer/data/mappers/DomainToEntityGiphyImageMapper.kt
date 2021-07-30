package zaloznaya.olga.app.gifviewer.data.mappers

import zaloznaya.olga.app.gifviewer.data.device.db.entity.GifImageEntity
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

class DomainToEntityGiphyImageMapper : IMapper<GifImage, GifImageEntity> {
    override fun mapFrom(from: GifImage): GifImageEntity =
        GifImageEntity(
            id = from.id,
            title = from.title,
            urlOriginal = from.urlOriginal,
            urlPreview = from.urlPreview,
            markedAsDeleted = false
        )
}