package zaloznaya.olga.app.gifviewer.domain.usecase

import kotlinx.coroutines.flow.Flow
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import zaloznaya.olga.app.gifviewer.domain.usecase.base.usecasetypes.BaseUseCaseWitOuthParams
import zaloznaya.olga.app.gifviewer.utils.BaseResult

class ObserveImagesFromDbUseCase(
    private val repository: IGiphyRepository
) : BaseUseCaseWitOuthParams<Flow<List<GifImage>>> {

    override suspend fun run(
        onComplete: (BaseResult<Flow<List<GifImage>>>) -> Unit,
        onError: (BaseResult<String>) -> Unit
    ) {
        try {
            val result = repository.getGifImagesFromDb()
            onComplete(
                BaseResult.success(result)
            )
        } catch (e: Exception) {
            onError(
                BaseResult.error(e.localizedMessage)
            )
        }
    }

}