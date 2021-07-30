package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.usecase.*
import zaloznaya.olga.app.gifviewer.utils.TAG
import javax.inject.Inject

@HiltViewModel
class ImagesListViewModel @Inject constructor(
    private val getTrendingImagesUseCase: GetTrendingImagesUseCase,
    private val searchImagesUseCase: SearchImagesUseCase,
    private val markImageAsDeletedUseCase: MarkImageAsDeletedUseCase,
    private val getDeletedImagesFromDbUseCase: GetDeletedImagesFromDbUseCase,
    private val observeImagesFromDbUseCase: ObserveImagesFromDbUseCase
) : ViewModel() {

    private val listImages = MutableLiveData<ArrayList<GifImage>>()
    fun getImages() = listImages

    var loading = MutableLiveData(false)

    // Pagination
    private val limit = 20
    private var page = 0

    // Search
    private var isSearch = false
    private var query: String = ""

    init {
        getTrendingImages()
//        observeImagesFromDb()
    }

    private fun getTrendingImages() {
        isSearch = false
        try {
            loading.postValue(true)
            viewModelScope.launch {
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
            }
            loading.postValue(false)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.localizedMessage}")
            e.printStackTrace()
            loading.postValue(false)
        }
    }

    private fun searchImages() {
        isSearch = true
        try {
            loading.postValue(true)
            viewModelScope.launch {
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
            }
            loading.postValue(false)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.localizedMessage}")
            e.printStackTrace()
            loading.postValue(false)
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
        try {
            viewModelScope.launch {
                markImageAsDeletedUseCase.run(MarkImageAsDeletedUseCase.Params(id))

                val list = listImages.value
                listImages.value?.forEachIndexed { index, image ->
                    if (image.id == id) {
                        list?.removeAt(index)
                        listImages.postValue(list)
                        return@launch
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.localizedMessage}")
            e.printStackTrace()
        }
    }

    private suspend fun filterImages(list: List<GifImage>): ArrayList<GifImage> {
        val result = arrayListOf<GifImage>()
        val deletedImages = getDeletedImagesFromDbUseCase.run()
        list.forEach { image ->
            if (!deletedImages.contains(image.id)) {
                result.add(image)
            }
        }
        return result
    }

    private fun observeImagesFromDb() {
        try {
            viewModelScope.launch {
                observeImagesFromDbUseCase.run().collect {
                    Log.d(TAG, "ImagesFromDb = $it")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.localizedMessage}")
            e.printStackTrace()
        }
    }
}