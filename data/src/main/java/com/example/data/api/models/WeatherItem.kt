package com.example.data.api.models

import kotlinx.serialization.Serializable

@Serializable
data class WeatherItem(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
