package zaloznaya.olga.app.gifviewer.data.mappers

import zaloznaya.olga.app.gifviewer.data.device.db.entity.GifImageEntity
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

class DomainFromEntityGiphyImageMapper : IMapper<GifImageEntity, GifImage> {
    override fun mapFrom(from: GifImageEntity): GifImage =
        GifImage(
            id = from.id,
            title = from.title,
            urlOriginal = from.urlOriginal,
            urlPreview = from.urlPreview
        )
}