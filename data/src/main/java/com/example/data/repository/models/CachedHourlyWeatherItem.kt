package com.example.data.repository.models

data class CachedHourlyWeatherItem(
    val date: Long,

    val temperature: Int,

    val icon: String,
)