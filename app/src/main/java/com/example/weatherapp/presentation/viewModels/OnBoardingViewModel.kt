package com.example.weatherapp.presentation.viewModels

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCases.ChangeCityUseCase
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val changeCityUseCase: ChangeCityUseCase,
) : ViewModel() {

    private val _showCityChangeResultToast = MutableLiveData<Int>()
    val showCityChangeResultToast: LiveData<Int> = _showCityChangeResultToast

    fun changeCity(cityName: String) {
        viewModelScope.launch {
            _showCityChangeResultToast.value = when (changeCityUseCase.execute(cityName)) {
                0, 1 -> 1
                else -> -1
            }
        }
    }

    fun searchViewOnQueryTextListener(searchView: SearchView): SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    changeCity(query.removePrefix(" ").removeSuffix(" ").lowercase())
                    searchView.setQuery("", false)
                    searchView.clearFocus()
                    return false
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        }
}
