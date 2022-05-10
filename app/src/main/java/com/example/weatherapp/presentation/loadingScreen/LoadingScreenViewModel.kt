package com.example.weatherapp.presentation.loadingScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCases.LoadWeatherDataUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingScreenViewModel(
    private val loadWeatherDataUseCase: LoadWeatherDataUseCase
) :
    ViewModel() {

    private val _dataIsDownloaded = MutableLiveData<Boolean>()
    val dataIsDownloaded: LiveData<Boolean> = _dataIsDownloaded


    init {
        viewModelScope.launch {
            _dataIsDownloaded.value = loadWeatherDataUseCase.execute()
        }
    }
}