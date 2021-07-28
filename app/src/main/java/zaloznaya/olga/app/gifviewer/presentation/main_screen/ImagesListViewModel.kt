package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.usecase.GetTrendingImagesUseCase
import zaloznaya.olga.app.gifviewer.utils.TAG
import javax.inject.Inject

@HiltViewModel
class ImagesListViewModel @Inject constructor(
    private val getTrendingImagesUseCase: GetTrendingImagesUseCase
) : ViewModel() {

    private val listImages = MutableLiveData<List<GifImage>>()
    fun getImages() = listImages

    var loading = MutableLiveData(false)

    init {
        getTrendingImages()
    }

    private fun getTrendingImages() {
        try {
            loading.postValue(true)
            viewModelScope.launch {
                val result = getTrendingImagesUseCase
                    .run(GetTrendingImagesUseCase.Params(20, 0))
                listImages.postValue(result)
            }
            loading.postValue(false)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.localizedMessage}")
            e.printStackTrace()
            loading.postValue(false)
        }
    }

    fun removeImage(id: String) {
        Log.d(TAG, "removeImage: $id")
    }
}