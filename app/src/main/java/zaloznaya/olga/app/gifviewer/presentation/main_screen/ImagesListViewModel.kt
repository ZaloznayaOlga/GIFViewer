package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.usecase.*
import zaloznaya.olga.app.gifviewer.utils.TAG

class ImagesListViewModel (
    private val getTrendingImagesUseCase: GetTrendingImagesUseCase,
    private val searchImagesUseCase: SearchImagesUseCase,
    private val markImageAsDeletedUseCase: MarkImageAsDeletedUseCase,
    private val getDeletedImagesFromDbUseCase: GetDeletedImagesFromDbUseCase,
    private val observeImagesFromDbUseCase: ObserveImagesFromDbUseCase
) : ViewModel(), KoinComponent {

    private val listImages = MutableLiveData<ArrayList<GifImage>>()
    fun getImages() = listImages

    var loading = MutableLiveData(false)
    var isNoConnection = MutableLiveData(false)

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
        loading.postValue(true)
        isNoConnection.postValue(false)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = filterImages(
                    getTrendingImagesUseCase.run(
                        GetTrendingImagesUseCase.Params(limit, limit * page)
                    )
                )
                if (listImages.value.isNullOrEmpty()) {
                    listImages.postValue(result)
                } else {
                    listImages.value?.let { list ->
                        list.addAll(result)
                        listImages.postValue(list)
                    }
                }
                loading.postValue(false)
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "Exception: ${e.localizedMessage}")
                e.printStackTrace()
                observeImagesFromDb()
                loading.postValue(false)
            }
        }
    }

    private fun searchImages() {
        isSearch = true
        loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = filterImages(
                    searchImagesUseCase.run(
                        SearchImagesUseCase.Params(query, limit, limit * page)
                    )
                )
                if (listImages.value.isNullOrEmpty()) {
                    listImages.postValue(result)
                } else {
                    listImages.value?.let { list ->
                        list.addAll(result)
                        listImages.postValue(list)
                    }
                }
                isNoConnection.postValue(false)
                loading.postValue(false)
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "Exception: ${e.localizedMessage}")
                e.printStackTrace()
                isNoConnection.postValue(true)
                loading.postValue(false)
            }
        }
    }

    fun newSearchImages(query: String) {
        listImages.postValue(arrayListOf())
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
        listImages.postValue(arrayListOf())
        page = 0
        this.query = ""
        getTrendingImages()
    }

    fun removeImage(id: String) {
        Log.d(TAG, "removeImage: $id")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                markImageAsDeletedUseCase.run(MarkImageAsDeletedUseCase.Params(id))

                val list = listImages.value
                listImages.value?.forEachIndexed { index, image ->
                    if (image.id == id) {
                        list?.removeAt(index)
                        listImages.postValue(list)
                        return@launch
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "Exception: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

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

    private fun observeImagesFromDb() {
        loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            observeImagesFromDbUseCase.run( { result ->
                // onComplete
                viewModelScope.launch(Dispatchers.IO) {
                    result.data.collect {
                        listImages.postValue(filterImages(it))
                        loading.postValue(false)
                    }
                }
            }, { message ->
                // onError
                Log.e(TAG, "Exception: $message")
                loading.postValue(false)
                isNoConnection.postValue(true)
            })

//            try {
//                observeImagesFromDbUseCase.run().collect {
//                    listImages.postValue(filterImages(it))
//                    loading.postValue(false)
//                }
//            } catch (e: java.lang.Exception) {
//                Log.e(TAG, "Exception: ${e.localizedMessage}")
//                e.printStackTrace()
//                loading.postValue(false)
//                isNoConnection.postValue(true)
//            }
        }
    }
}