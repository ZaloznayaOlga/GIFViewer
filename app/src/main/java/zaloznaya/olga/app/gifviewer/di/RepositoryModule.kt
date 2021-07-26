package zaloznaya.olga.app.gifviewer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zaloznaya.olga.app.gifviewer.data.datasource.IRemoteGiphyDataSource
import zaloznaya.olga.app.gifviewer.data.repositories.GiphyRepository
import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideGiphyRepository(
        dataSource: IRemoteGiphyDataSource
    ): IGiphyRepository = GiphyRepository(dataSource)
}