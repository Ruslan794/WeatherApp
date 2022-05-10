package com.example.weatherapp.presentation.mainScreens.homeScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.domain.models.City
import com.example.domain.models.DayWeatherExtendedItem
import com.example.domain.useCases.GetCurrentCityUseCase
import com.example.domain.useCases.ShowCurrentDayWeatherUseCase
import com.example.domain.useCases.ShowTomorrowDayWeatherUseCase
import com.example.weatherapp.presentation.common.WeatherIconsProvider
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class HomeScreenViewModel(
    private val showCurrentDayWeatherUseCase: ShowCurrentDayWeatherUseCase,
    private val showTomorrowDayWeatherUseCase: ShowTomorrowDayWeatherUseCase,
    private val getCurrentCityUseCase: GetCurrentCityUseCase,
    private val iconsProvider: WeatherIconsProvider
) :
    ViewModel() {

    private val _currentCity = MutableLiveData<City>()
    private val currentCity: LiveData<City> = _currentCity

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date
    private val _temperature = MutableLiveData<String>()
    val temperature: LiveData<String> = _temperature
    private val _windSpeed = MutableLiveData<String>()
    val windSpeed: LiveData<String> = _windSpeed
    private val _humidity = MutableLiveData<String>()
    val humidity: LiveData<String> = _humidity
    private val _minTemperature = MutableLiveData<String>()
    val minTemperature: LiveData<String> = _minTemperature
    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city
    private val _weatherInOneWord = MutableLiveData<String>()
    val weatherInOneWord: LiveData<String> = _weatherInOneWord
    private val _icon = MutableLiveData<String>()
    val icon: LiveData<String> = _icon


    private val _hourlyTemperatureFirst = MutableLiveData<String>()
    val hourlyTemperatureFirst: LiveData<String> = _hourlyTemperatureFirst
    private val _hourlyTemperatureSecond = MutableLiveData<String>()
    val hourlyTemperatureSecond: LiveData<String> = _hourlyTemperatureSecond
    private val _hourlyTemperatureThird = MutableLiveData<String>()
    val hourlyTemperatureThird: LiveData<String> = _hourlyTemperatureThird
    private val _hourlyTimeFirst = MutableLiveData<String>()
    val hourlyTimeFirst: LiveData<String> = _hourlyTimeFirst
    private val _hourlyTimeSecond = MutableLiveData<String>()
    val hourlyTimeSecond: LiveData<String> = _hourlyTimeSecond
    private val _hourlyTimeThird = MutableLiveData<String>()
    val hourlyTimeThird: LiveData<String> = _hourlyTimeThird
    private val _hourlyIconOne = MutableLiveData<String>()
    val hourlyIconOne: LiveData<String> = _hourlyIconOne
    private val _hourlyIconTwo = MutableLiveData<String>()
    val hourlyIconTwo: LiveData<String> = _hourlyIconTwo
    private val _hourlyIconThree = MutableLiveData<String>()
    val hourlyIconThree: LiveData<String> = _hourlyIconThree

    private val _todayTabActive = MutableLiveData(true)
    val todayTabActive: LiveData<Boolean> = _todayTabActive
    private val _tomorrowTabActive = MutableLiveData(false)
    val tomorrowTabActive: LiveData<Boolean> = _tomorrowTabActive

    private val _showConnectionErrorToast = MutableLiveData<Boolean>()
    val showConnectionErrorToast: LiveData<Boolean> = _showConnectionErrorToast


    init {
        viewModelScope.launch {
            _currentCity.value = getCurrentCityUseCase.execute()
            updateData(getCurrentDayWeather())
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("A", "VM cleared")
    }

    private suspend fun getCurrentDayWeather(): DayWeatherExtendedItem? =
        showCurrentDayWeatherUseCase.execute(currentCity.value!!)


    private suspend fun getTomorrowDayWeather(): DayWeatherExtendedItem? =
        showTomorrowDayWeatherUseCase.execute(currentCity.value!!)

    private fun updateData(weatherData: DayWeatherExtendedItem?) {

        if (weatherData == null) {
            _showConnectionErrorToast.value = true
            return
        }

        _date.value = editDataFormat(weatherData.date, 0)
        _temperature.value = weatherData.temperature.toString().plus("\u00B0")
        _windSpeed.value = weatherData.windSpeed.toString().plus(" km/h")
        _humidity.value = weatherData.humidity.toString().plus("%")
        _minTemperature.value = weatherData.minTemperature.toString().plus("\u00B0")
        _weatherInOneWord.value = weatherData.weatherInOneWord
        _icon.value = weatherData.icon

        _hourlyTemperatureFirst.value =
            weatherData.hourlyWeather[0].temperature.toString().plus("\u00B0")
        _hourlyTemperatureSecond.value =
            weatherData.hourlyWeather[1].temperature.toString().plus("\u00B0")
        _hourlyTemperatureThird.value =
            weatherData.hourlyWeather[2].temperature.toString().plus("\u00B0")
        _hourlyTimeFirst.value = editDataFormat(weatherData.hourlyWeather[0].time, 1)
        _hourlyTimeSecond.value = editDataFormat(weatherData.hourlyWeather[1].time, 1)
        _hourlyTimeThird.value = editDataFormat(weatherData.hourlyWeather[2].time, 1)
        _hourlyIconOne.value = weatherData.hourlyWeather[0].icon
        _hourlyIconTwo.value = weatherData.hourlyWeather[1].icon
        _hourlyIconThree.value = weatherData.hourlyWeather[2].icon

        _city.value = "${currentCity.value?.name}, ${currentCity.value?.country}"

    }

    fun onTodayTabClicked() {
        if (!todayTabActive.value!!) {
            viewModelScope.launch {
                _todayTabActive.value = true
                _tomorrowTabActive.value = false
                updateData(getCurrentDayWeather())
            }

        }
    }

    fun onTomorrowTabClicked() {
        if (!tomorrowTabActive.value!!) {
            viewModelScope.launch {
                _todayTabActive.value = false
                _tomorrowTabActive.value = true
                updateData(getTomorrowDayWeather())
            }
        }
    }

    fun onRefresh(swipeRefreshLayout: SwipeRefreshLayout) {
        viewModelScope.launch {
            if (todayTabActive.value!!) updateData(getCurrentDayWeather())
            else updateData(getTomorrowDayWeather())
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun editDataFormat(unformattedDate: Long, mode: Int): String {
        return when (mode) {
            0 -> {
                val simpleDateFormat = SimpleDateFormat("EE, MMM d", Locale.ENGLISH)
                simpleDateFormat.format(unformattedDate * 1000L)
            }
            1 -> {
                val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                val date = simpleDateFormat.format(unformattedDate * 1000L)
                date.replace('M', 'm')
            }
            else -> {
                val simpleDateFormat = SimpleDateFormat("EE, MMMM dddd yyyy", Locale.ENGLISH)
                simpleDateFormat.format(unformattedDate * 1000L)
            }
        }
    }

    fun getIcon(code: String): Int = iconsProvider.getIconByCode(code = code)
}