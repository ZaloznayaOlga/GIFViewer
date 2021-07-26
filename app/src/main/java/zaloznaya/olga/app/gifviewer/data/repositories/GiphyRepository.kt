package zaloznaya.olga.app.gifviewer.data.repositories

import zaloznaya.olga.app.gifviewer.data.datasource.IRemoteGiphyDataSource
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import javax.inject.Inject

class GiphyRepository @Inject constructor(
    private val dataSource: IRemoteGiphyDataSource
) : IGiphyRepository {

    override suspend fun getImages(limit: Int, offset: Int): List<GifImage>? =
        dataSource.getImages(limit, offset)
}