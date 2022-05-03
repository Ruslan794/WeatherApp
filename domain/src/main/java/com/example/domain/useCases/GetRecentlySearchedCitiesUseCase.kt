package com.example.domain.useCases

import com.example.domain.models.City
import com.example.domain.repositories.WeatherRepository

class GetRecentlySearchedCitiesUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(): List<City>? {
        weatherRepository.getRecentlySearchedCities().let {
            return it.ifEmpty { null }
        }
    }
}