package zaloznaya.olga.app.gifviewer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zaloznaya.olga.app.gifviewer.data.datasource.IDeviceGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.datasource.IRemoteGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.device.datasource.DeviceGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.device.db.dao.ImagesDao
import zaloznaya.olga.app.gifviewer.data.device.db.entity.GifImageEntity
import zaloznaya.olga.app.gifviewer.data.mappers.IMapper
import zaloznaya.olga.app.gifviewer.data.remote.datasource.RemoteGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.remote.models.DataDto
import zaloznaya.olga.app.gifviewer.data.remote.retrofit.GiphyApiService
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteGiphyDataSource(
        apiService: GiphyApiService,
        mapper: IMapper<List<DataDto>?, List<GifImage>>
    ) : IRemoteGiphyDataSource = RemoteGiphyDataSource(apiService, mapper)

    @Provides
    @Singleton
    fun provideDeviceGiphyDataSource(
        dao: ImagesDao,
        mapperFromEntity: IMapper<GifImageEntity, GifImage>,
        mapperToEntity: IMapper<GifImage, GifImageEntity>
    ) : IDeviceGiphyDataSource = DeviceGiphyDataSource(dao, mapperFromEntity, mapperToEntity)
}