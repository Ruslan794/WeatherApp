package com.example.domain.models

data class HourlyWeatherItem(
    val time : Long,
    val temperature: Int,
    val icon : String
)