package zaloznaya.olga.app.gifviewer.presentation.main_screen

sealed class State {
    class LoadingState: State()
    class LoadedState<T>(val data: List<T>): State()
    class NoItemsState: State()
    class ErrorState(val message: String): State()
}
