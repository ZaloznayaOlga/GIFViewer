package zaloznaya.olga.app.gifviewer.domain.usecase

import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import zaloznaya.olga.app.gifviewer.domain.usecase.base.usecasetypes.BaseUseCaseWitOuthParams

class GetDeletedImagesFromDbUseCase (
    private val repository: IGiphyRepository
) : BaseUseCaseWitOuthParams<List<String>> {

    override suspend fun run(): List<String> =
        repository.getDeletedImagesIds()

}