package zaloznaya.olga.app.gifviewer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zaloznaya.olga.app.gifviewer.data.mappers.IMapper
import zaloznaya.olga.app.gifviewer.data.mappers.RemoteToDomainGiphyImageMapper
import zaloznaya.olga.app.gifviewer.data.remote.models.DataDto
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

@Module
@InstallIn(SingletonComponent::class)
class MappersModule {

    @Provides
    internal fun provideRemoteToDomainGiphyImageMapper(): IMapper<List<DataDto>?, List<GifImage>> =
        RemoteToDomainGiphyImageMapper()
}