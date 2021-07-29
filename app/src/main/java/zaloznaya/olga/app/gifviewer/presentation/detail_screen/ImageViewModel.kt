package zaloznaya.olga.app.gifviewer.presentation.detail_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor() : ViewModel() {

    private val listImages = MutableLiveData<ArrayList<GifImage>>()
    fun getImages() = listImages

    val backActionLiveEvent = SingleLiveEvent<Any>()

    fun setInputParamsImage(image : GifImage) {
        listImages.postValue(arrayListOf(image))
    }

    fun onBackClicked() {
        backActionLiveEvent.call()
    }
}