package zaloznaya.olga.app.gifviewer.data.datasource

import zaloznaya.olga.app.gifviewer.domain.model.GifImage

interface IRemoteGiphyDataSource {

    suspend fun getImages(limit: Int, offset: Int): List<GifImage>?
    suspend fun searchImages(query: String, limit: Int, offset: Int): List<GifImage>?
}