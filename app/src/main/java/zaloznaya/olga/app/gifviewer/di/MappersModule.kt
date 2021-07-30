package zaloznaya.olga.app.gifviewer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zaloznaya.olga.app.gifviewer.data.device.db.entity.GifImageEntity
import zaloznaya.olga.app.gifviewer.data.mappers.DomainFromEntityGiphyImageMapper
import zaloznaya.olga.app.gifviewer.data.mappers.DomainToEntityGiphyImageMapper
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

    @Provides
    internal fun provideDomainToEntityGiphyImageMapper(): IMapper<GifImage, GifImageEntity> =
        DomainToEntityGiphyImageMapper()

    @Provides
    internal fun provideDomainFromEntityGiphyImageMapper(): IMapper<GifImageEntity, GifImage> =
        DomainFromEntityGiphyImageMapper()
}