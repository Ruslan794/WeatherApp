package com.example.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyItem (
    val dt: Long,
    val temp: Temperature,
    val humidity: Int,

    @SerialName("wind_speed")
    val windSpeed: Double,

    val weather: List<WeatherItem>
)