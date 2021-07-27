package zaloznaya.olga.app.gifviewer.domain.usecase.base.usecasetypes

interface BaseUseCaseWithParams<Results, Params> {

    suspend fun run(params : Params) : Results

}