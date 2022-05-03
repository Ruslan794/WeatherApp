package com.example.domain.useCases

import com.example.domain.models.City
import com.example.domain.models.DayWeatherItem
import com.example.domain.repositories.WeatherRepository

class ShowDailyForecastUseCase (private val weatherRepository: WeatherRepository) {
    suspend fun execute(currentCity: City): List <DayWeatherItem> = weatherRepository.getDailyForecast(currentCity)
}