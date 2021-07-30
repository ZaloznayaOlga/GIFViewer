package zaloznaya.olga.app.gifviewer.domain.usecase

import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import zaloznaya.olga.app.gifviewer.domain.usecase.base.usecasetypes.BaseUseCaseWithParams
import javax.inject.Inject

class MarkImageAsDeletedUseCase @Inject constructor(
    private val repository: IGiphyRepository
) : BaseUseCaseWithParams<Unit, MarkImageAsDeletedUseCase.Params> {

    override suspend fun run(params: Params) =
        repository.markAsDeleted(params.id)

    class Params(val id: String)
}