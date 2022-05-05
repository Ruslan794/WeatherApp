package com.example.weatherapp.presentation.mainScreens.dailyForecastScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.domain.models.City
import com.example.domain.models.DayWeatherItem
import com.example.domain.useCases.GetCurrentCityUseCase
import com.example.domain.useCases.ShowDailyForecastUseCase
import kotlinx.coroutines.launch


class DailyForecastViewModel(
    private val showDailyForecastUseCase: ShowDailyForecastUseCase,
    private val getCurrentCityUseCase: GetCurrentCityUseCase
) :
    ViewModel() {

    private val _weatherDataList = MutableLiveData<List<DayWeatherItem>>()
    val weatherDataList: LiveData<List<DayWeatherItem>> = _weatherDataList

    private val _currentCity = MutableLiveData<City>()

    private val _showConnectionErrorToast = MutableLiveData<Boolean>()
    val showConnectionErrorToast: LiveData<Boolean> = _showConnectionErrorToast

    init {
        viewModelScope.launch {
            _currentCity.value = getCurrentCityUseCase.execute()
            updateData()
        }
    }

    private fun updateData() {
        viewModelScope.launch {
            val result = showDailyForecastUseCase.execute(_currentCity.value!!)
            if (result.isEmpty()) _showConnectionErrorToast.value = true
            else _weatherDataList.value = result
        }
    }

    fun onRefresh(swipeRefreshLayout: SwipeRefreshLayout) {
        viewModelScope.launch {
            updateData()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    fun onWeatherChanged(list: List<DayWeatherItem>, adapter: DailyWeatherAdapter) {
        adapter.weatherList = list
        adapter.notifyDataSetChanged()
    }
}