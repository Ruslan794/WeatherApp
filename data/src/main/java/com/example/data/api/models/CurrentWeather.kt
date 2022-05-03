package com.example.data.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather (
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val hourly: List<HourlyItem>,
    val daily: List<DailyItem>,
)