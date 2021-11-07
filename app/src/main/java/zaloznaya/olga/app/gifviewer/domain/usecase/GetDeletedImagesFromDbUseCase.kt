package zaloznaya.olga.app.gifviewer.domain.usecase

import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import zaloznaya.olga.app.gifviewer.domain.usecase.base.usecasetypes.BaseUseCaseWitOuthParams
import zaloznaya.olga.app.gifviewer.utils.BaseResult

class GetDeletedImagesFromDbUseCase (
    private val repository: IGiphyRepository
) {

    suspend fun run(): BaseResult<List<String>> {
        return try {
            val result = repository.getDeletedImagesIds()
            BaseResult.success(result)
        } catch (e: Exception) {
            BaseResult.error(message = e.localizedMessage)
        }
    }

}