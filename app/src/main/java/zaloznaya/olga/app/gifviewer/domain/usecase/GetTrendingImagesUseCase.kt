package zaloznaya.olga.app.gifviewer.domain.usecase

import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import zaloznaya.olga.app.gifviewer.domain.usecase.base.usecasetypes.BaseUseCaseWithParams

class GetTrendingImagesUseCase (
    private val repository: IGiphyRepository
) : BaseUseCaseWithParams<List<GifImage>, GetTrendingImagesUseCase.Params> {

    override suspend fun run(params: Params): List<GifImage> =
        repository.getImages(params.limit, params.offset) ?: listOf()

    class Params(val limit: Int, val offset: Int)
}