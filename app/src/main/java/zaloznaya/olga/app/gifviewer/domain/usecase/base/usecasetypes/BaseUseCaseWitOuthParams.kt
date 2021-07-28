package zaloznaya.olga.app.gifviewer.domain.usecase.base.usecasetypes

interface BaseUseCaseWitOuthParams<Results> {

    suspend fun run() : Results

}