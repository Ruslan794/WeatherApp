package com.example.data.api.models

import kotlinx.serialization.Serializable

@Serializable
data class HourlyItem(
    val dt: Long,
    val temp: Double,
    val weather: List<WeatherItem>
)
