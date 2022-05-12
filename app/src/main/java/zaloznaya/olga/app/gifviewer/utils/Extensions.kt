package zaloznaya.olga.app.gifviewer.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Set default value for any type of MutableLiveData
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
// Set new value for any tye of MutableLiveData
fun <T> MutableLiveData<T>.set(newValue: T) = apply { setValue(newValue) }

suspend fun <T> MutableLiveData<T>.setInMain(newValue: T) = apply {
    withContext(Dispatchers.Main) {
        setValue(newValue)
    }
}