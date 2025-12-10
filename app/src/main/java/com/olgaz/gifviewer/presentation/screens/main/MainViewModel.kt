package com.olgaz.gifviewer.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olgaz.gifviewer.data.remote.onErrorValue
import com.olgaz.gifviewer.data.remote.onSuccessValue
import com.olgaz.gifviewer.domain.usecase.GetTrendingImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTrendingImagesUseCase: GetTrendingImagesUseCase
): ViewModel() {

    init {
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch {
            getTrendingImagesUseCase().collect { result ->
                result.onSuccessValue { list ->
                    Log.d("MainViewModel", "Images LIST:")
                    list.forEach {
                        Log.d("MainViewModel", it.toString())
                    }
                }.onErrorValue {
                    Log.e("MainViewModel", "Error: $it")
                }
            }
        }
    }
}