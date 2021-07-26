package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.domain.repositories.IGiphyRepository
import javax.inject.Inject

@HiltViewModel
class ImagesListViewModel @Inject constructor(
    private val repository: IGiphyRepository
) : ViewModel() {

    private val listImages = MutableLiveData<List<GifImage>>()

    fun getImages() {
        viewModelScope.launch {
            try {
                val list = repository.getImages(limit = 20, offset = 0)
                listImages.postValue(list)
                Log.d("ImagesList", "Print LIST:")
                list?.forEach {
                    Log.d("", it.toString())
                }
            } catch (e: Exception){
                Log.e("ImagesList", "Exception: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

}