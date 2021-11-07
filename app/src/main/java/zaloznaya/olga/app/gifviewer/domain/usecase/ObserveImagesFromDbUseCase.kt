package zaloznaya.olga.app.gifviewer.domain.usecase

import kotlinx.coroutines.flow.Flow
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import zaloznaya.olga.app.gifviewer.domain.usecase.base.usecasetypes.BaseUseCaseWitOuthParams

class ObserveImagesFromDbUseCase (
    private val repository: IGiphyRepository
) : BaseUseCaseWitOuthParams<Flow<List<GifImage>>> {

    override suspend fun run(): Flow<List<GifImage>> =
        repository.getGifImagesFromDb()

}