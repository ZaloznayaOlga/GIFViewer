package zaloznaya.olga.app.gifviewer.presentation.detail_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.utils.SingleLiveEvent

class ImageViewModel : ViewModel(), KoinComponent {

    private val listImagesWithPosition = MutableLiveData<Pair<ArrayList<GifImage>, Int>>()
    fun getImagesWithPosition(): LiveData<Pair<ArrayList<GifImage>, Int>> = listImagesWithPosition

    val backActionLiveEvent = SingleLiveEvent<Any>()
    val paymentClickEvent = SingleLiveEvent<Any>()

    fun setInputParamsImage(images : ArrayList<GifImage>, position: Int) {
        images.let { list ->
            listImagesWithPosition.postValue(
                Pair(list, position)
            )
        }
    }

    fun onBackClicked() {
        backActionLiveEvent.call()
    }

    fun onBuyAndDownload() {
        paymentClickEvent.call()
    }
}