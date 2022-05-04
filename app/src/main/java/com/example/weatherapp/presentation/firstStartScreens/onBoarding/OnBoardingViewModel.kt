package com.example.weatherapp.presentation.firstStartScreens.onBoarding

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.City
import com.example.domain.useCases.ChangeCityUseCase
import com.example.weatherapp.presentation.common.CityAdapter
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val changeCityUseCase: ChangeCityUseCase,
) : ViewModel() {

    private val _showCityChangeResultToast = MutableLiveData<Int>()
    val showCityChangeResultToast: LiveData<Int> = _showCityChangeResultToast
    private val _cityList = MutableLiveData<List<City?>>()
    val cityList: LiveData<List<City?>> = _cityList

    init {
        _cityList.value = getDefaultValuesForCityList()
    }

    fun changeCity(cityName: String) {
        viewModelScope.launch {
            _showCityChangeResultToast.value = when (changeCityUseCase.execute(cityName.lowercase())) {
                -1 -> -1
                0 -> 0
                1 -> 1
                2 -> 2
                else -> -2
            }
        }
    }

    fun searchViewOnQueryTextListener(searchView: SearchView): SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    changeCity(query.removePrefix(" ").removeSuffix(" "))
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

    private fun getDefaultValuesForCityList(): List<City> = OnBoardingCityList.cities

    fun onCityListChanged(list: List<City?>, adapter: CityAdapter) {
        adapter.cityList = list
        adapter.notifyDataSetChanged()
    }
}
