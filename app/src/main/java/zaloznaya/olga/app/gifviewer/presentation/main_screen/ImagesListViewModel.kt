package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.util.Log
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

    init {
        getTrendingImages()
    }

    private fun getTrendingImages() {
        try {
            viewModelScope.launch {
                val result = getTrendingImagesUseCase
                    .run(GetTrendingImagesUseCase.Params(20, 0))
                listImages.postValue(result)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.localizedMessage}")
            e.printStackTrace()
        }
    }

}