package com.example.domain.useCases

import com.example.domain.repositories.WeatherRepository

class ChangeCityUseCase(private val weatherRepository: WeatherRepository) {

    suspend fun execute(cityName: String): Int {
        return weatherRepository.getCityByName(cityName)
    }
}