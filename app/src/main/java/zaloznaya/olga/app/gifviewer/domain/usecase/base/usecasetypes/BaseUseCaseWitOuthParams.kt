package zaloznaya.olga.app.gifviewer.domain.usecase.base.usecasetypes

import zaloznaya.olga.app.gifviewer.utils.BaseResult

interface BaseUseCaseWitOuthParams<T: Any> {

    suspend fun run (
        onComplete: (BaseResult<T>) -> Unit,
        onError: (BaseResult<String>) -> Unit
    )

}