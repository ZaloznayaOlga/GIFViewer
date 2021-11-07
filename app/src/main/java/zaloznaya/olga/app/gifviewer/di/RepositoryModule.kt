package zaloznaya.olga.app.gifviewer.di

import org.koin.dsl.module
import zaloznaya.olga.app.gifviewer.data.datasource.IDeviceGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.datasource.IRemoteGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.repositories.GiphyRepository
import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository

val RepositoryModule = module {

    single<IGiphyRepository> {
        GiphyRepository(get<IRemoteGiphyDataSource>(), get<IDeviceGiphyDataSource>())
    }

}