package zaloznaya.olga.app.gifviewer.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import zaloznaya.olga.app.gifviewer.domain.usecase.*
import zaloznaya.olga.app.gifviewer.presentation.detail_screen.ImageViewModel
import zaloznaya.olga.app.gifviewer.presentation.main_screen.ImagesListViewModel

val ViewModelModule = module {

    viewModel<ImageViewModel> { ImageViewModel() }
    viewModel<ImagesListViewModel> { ImagesListViewModel(
        get<GetTrendingImagesUseCase>(),
        get<SearchImagesUseCase>(),
        get<MarkImageAsDeletedUseCase>(),
        get<GetDeletedImagesFromDbUseCase>(),
        get<ObserveImagesFromDbUseCase>()
    ) }

}