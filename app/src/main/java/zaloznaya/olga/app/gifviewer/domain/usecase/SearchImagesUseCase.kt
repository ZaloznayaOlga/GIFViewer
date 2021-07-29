package zaloznaya.olga.app.gifviewer.domain.usecase

import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import zaloznaya.olga.app.gifviewer.domain.usecase.base.usecasetypes.BaseUseCaseWithParams
import javax.inject.Inject

class SearchImagesUseCase @Inject constructor(
    private val repository: IGiphyRepository
) : BaseUseCaseWithParams<List<GifImage>, SearchImagesUseCase.Params> {

    override suspend fun run(params: Params): List<GifImage> =
        repository.searchImages(params.query, params.limit, params.offset) ?: listOf()

    class Params(val query: String, val limit: Int, val offset: Int)
}