package zaloznaya.olga.app.gifviewer.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import zaloznaya.olga.app.gifviewer.data.mappers.DomainFromEntityGiphyImageMapper
import zaloznaya.olga.app.gifviewer.data.mappers.DomainToEntityGiphyImageMapper
import zaloznaya.olga.app.gifviewer.data.mappers.RemoteToDomainGiphyImageMapper

val MappersModule = module {

    factory(named("RemoteToDomainGiphyImageMapper")) {
        RemoteToDomainGiphyImageMapper()
    }

    factory(named("DomainToEntityGiphyImageMapper")) {
        DomainToEntityGiphyImageMapper()
    }

    factory(named("DomainFromEntityGiphyImageMapper")) {
        DomainFromEntityGiphyImageMapper()
    }

}