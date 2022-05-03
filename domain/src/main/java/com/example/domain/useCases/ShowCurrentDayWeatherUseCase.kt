package com.example.domain.useCases

import com.example.domain.models.City
import com.example.domain.models.DayWeatherExtendedItem
import com.example.domain.repositories.WeatherRepository

class ShowCurrentDayWeatherUseCase (private val weatherRepository: WeatherRepository) {
    suspend fun execute(currentCity: City): DayWeatherExtendedItem? = weatherRepository.getCurrentDayWeather(currentCity)
}