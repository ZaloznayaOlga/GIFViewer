package zaloznaya.olga.app.gifviewer.domain.repositories

import zaloznaya.olga.app.gifviewer.domain.model.GifImage

interface IGiphyRepository {
    suspend fun getImages(limit: Int, offset: Int): List<GifImage>?
}