package zaloznaya.olga.app.gifviewer.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import zaloznaya.olga.app.gifviewer.data.datasource.IDeviceGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.datasource.IRemoteGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.device.datasource.DeviceGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.device.db.dao.ImagesDao
import zaloznaya.olga.app.gifviewer.data.remote.datasource.RemoteGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.remote.retrofit.GiphyApiService

val DataSourceModule = module {

    single<IRemoteGiphyDataSource> {
        RemoteGiphyDataSource(
            get<GiphyApiService>(),
            get((named("RemoteToDomainGiphyImageMapper")))
        )
    }

    single<IDeviceGiphyDataSource> {
        DeviceGiphyDataSource(
            get<ImagesDao>(),
            get(named("DomainFromEntityGiphyImageMapper")),
            get(named("DomainToEntityGiphyImageMapper"))
        )
    }

}