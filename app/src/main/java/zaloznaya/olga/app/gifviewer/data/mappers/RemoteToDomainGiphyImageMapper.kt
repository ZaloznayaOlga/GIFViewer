package zaloznaya.olga.app.gifviewer.data.mappers

import zaloznaya.olga.app.gifviewer.data.remote.models.DataDto
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

class RemoteToDomainGiphyImageMapper : IMapper<List<DataDto>?, List<GifImage>> {
    override fun mapFrom(from: List<DataDto>?): List<GifImage> {
        return from?.map { data ->
            GifImage(
                id = data.id,
                title = data.title,
                urlOriginal = data.images.original.url,
                urlPreview = data.images.previewGif.url,
            )
        } ?: listOf()
    }
}