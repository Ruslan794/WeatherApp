package com.example.domain.models

data class DayWeatherExtendedItem(
    val date: Long,
    val temperature: Int,
    val windSpeed: Int,
    val humidity: Int,
    val minTemperature: Int,
    val icon : String,
    val weatherInOneWord : String,
    val hourlyWeather : List<HourlyWeatherItem>,
)