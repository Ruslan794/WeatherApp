package com.example.data.repository.models

data class CachedDailyWeatherItem(
    val date: Long,

    val minTemperature: Int,

    val eveTemperature: Int,

    val humidity: Int,

    val windSpeed: Int,

    val icon: String,

    val description: String,

    val hourlyWeather: List<CachedHourlyWeatherItem>,
)