package com.example.domain.useCases

import com.example.domain.models.City
import com.example.domain.repositories.WeatherRepository

class LoadWeatherDataUseCase (private val weatherRepository: WeatherRepository) {
    suspend fun execute() = weatherRepository.loadData()
}