package com.example.domain.useCases

import com.example.domain.models.City
import com.example.domain.repositories.WeatherRepository

class GetCurrentCityUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(): City? = weatherRepository.getCurrentCity()
}