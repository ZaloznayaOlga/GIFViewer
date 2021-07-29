package zaloznaya.olga.app.gifviewer.presentation.detail_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor() : ViewModel() {

    private val listImages = MutableLiveData<ArrayList<GifImage>>()
    fun getImages() = listImages

    fun setInputParamsImage(image : GifImage) {
        listImages.postValue(arrayListOf(image))
    }
}