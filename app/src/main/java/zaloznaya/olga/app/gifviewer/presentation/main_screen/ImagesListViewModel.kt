package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.usecase.*
import zaloznaya.olga.app.gifviewer.presentation.ui.components.State
import zaloznaya.olga.app.gifviewer.utils.TAG
import zaloznaya.olga.app.gifviewer.utils.set
import zaloznaya.olga.app.gifviewer.utils.setInMain

class ImagesListViewModel(
    private val getTrendingImagesUseCase: GetTrendingImagesUseCase,
    private val searchImagesUseCase: SearchImagesUseCase,
    private val markImageAsDeletedUseCase: MarkImageAsDeletedUseCase,
    private val getDeletedImagesFromDbUseCase: GetDeletedImagesFromDbUseCase,
    private val observeImagesFromDbUseCase: ObserveImagesFromDbUseCase
) : ViewModel(), KoinComponent {

    private val state: MutableLiveData<State> = MutableLiveData<State>(State.LoadingState())
    fun getImagesState() = state
//    private val listImages = MutableLiveData<ArrayList<GifImage>>()

    // Pagination
    private val limit = 50
    private var page = 0

    // Search
    private var isSearch = false
    private var query: String = ""

    init {
        getTrendingImages()
    }

    private fun getTrendingImages() {
        isSearch = false
        state.set(newValue = State.LoadingState())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = filterImages(
                    getTrendingImagesUseCase.run(
                        GetTrendingImagesUseCase.Params(limit, limit * page)
                    )
                )
                if (result.isEmpty()) {
                    state.setInMain(newValue = State.NoItemsState())
                } else {
                    state.setInMain(newValue = State.LoadedState(data = result))
                }
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "Exception: ${e.localizedMessage}")
                state.setInMain(newValue = State.ErrorState("No Connection"))
//                observeImagesFromDb()
            }
        }
    }

    private fun searchImages() {
        isSearch = true
        state.set(newValue = State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = filterImages(
                    searchImagesUseCase.run(
                        SearchImagesUseCase.Params(query, limit, limit * page)
                    )
                )
                if (result.isEmpty()) {
                    state.setInMain(newValue = State.NoItemsState())
                } else {
                    state.setInMain(newValue = State.LoadedState(data = result))
                }
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "Exception: ${e.localizedMessage}")
                state.set(newValue = State.ErrorState("No Connection"))
            }
        }
    }

    fun newSearchImages(query: String) {
//        listImages.postValue(arrayListOf())
        state.set(newValue = State.LoadingState())
        page = 0
        this.query = query
        searchImages()
    }

    fun loadNextPage() {
        page += 1
        if (isSearch) searchImages() else getTrendingImages()
        Log.d(TAG, "loadNextPage: $page")
    }

    fun reloadTrendingImages() {
//        listImages.postValue(arrayListOf())
        state.set(newValue = State.LoadingState())
        page = 0
        this.query = ""
        getTrendingImages()
    }

//    fun removeImage(id: String) {
//        Log.d(TAG, "removeImage: $id")
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                markImageAsDeletedUseCase.run(MarkImageAsDeletedUseCase.Params(id))
//
//                val list = listImages.value
//                listImages.value?.forEachIndexed { index, image ->
//                    if (image.id == id) {
//                        list?.removeAt(index)
//                        listImages.postValue(list)
//                        return@launch
//                    }
//                }
//            } catch (e: java.lang.Exception) {
//                Log.e(TAG, "Exception: ${e.localizedMessage}")
//                e.printStackTrace()
//            }
//        }
//    }

    private suspend fun filterImages(list: List<GifImage>): ArrayList<GifImage> {
        val result = arrayListOf<GifImage>()
        val deletedImages = getDeletedImagesFromDbUseCase.run().data
        list.forEach { image ->
            if (!deletedImages.contains(image.id)) {
                result.add(image)
            }
        }
        return result
    }

    private suspend fun observeImagesFromDb() {
        Log.e(TAG, "observeImagesFromDb")
        state.setInMain(newValue = State.LoadingState())

        Log.e(TAG, "observeImagesFromDb run...")
        observeImagesFromDbUseCase.run({ result ->
            // onComplete
            viewModelScope.launch(Dispatchers.IO) {
                result.data.collect {
//                        listImages.postValue(filterImages(it))

                    if (it.isEmpty()) {
                        withContext(Dispatchers.Main) {
                            state.set(newValue = State.NoItemsState())
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            state.set(newValue = State.LoadedState(data = it))
                        }
                    }
                }
            }
        }, { message ->
            // onError
            Log.e(TAG, "Exception: $message")
            state.set(newValue = State.ErrorState("Exception: $message"))
        })
    }
}