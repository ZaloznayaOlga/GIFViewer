package zaloznaya.olga.app.gifviewer.di

import org.koin.dsl.module
import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import zaloznaya.olga.app.gifviewer.domain.usecase.*

val UseCasesModule = module {
    factory {
        GetDeletedImagesFromDbUseCase(repository = get<IGiphyRepository>())
    }
    factory {
        GetTrendingImagesUseCase(repository = get<IGiphyRepository>())
    }
    factory {
        MarkImageAsDeletedUseCase(repository = get<IGiphyRepository>())
    }
    factory {
        ObserveImagesFromDbUseCase(repository = get<IGiphyRepository>())
    }
    factory {
        SearchImagesUseCase(repository = get<IGiphyRepository>())
    }
}