package com.example.domain.repositories

import com.example.domain.models.City
import com.example.domain.models.DayWeatherExtendedItem
import com.example.domain.models.DayWeatherItem

interface WeatherRepository {

    suspend fun getCurrentDayWeather(city: City): DayWeatherExtendedItem?

    suspend fun getTomorrowDayWeather(city: City): DayWeatherExtendedItem?

    suspend fun getDailyForecast(city: City): List<DayWeatherItem>

    suspend fun getCityByName(cityName: String): Int

    suspend fun getCurrentCity(): City?

    suspend fun getRecentlySearchedCities(): List<City>

    suspend fun loadData(): Boolean

}