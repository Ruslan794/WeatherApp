package com.example.data.repository.models

import com.example.data.database.entities.DailyWeatherDataItem

data class CachedWeatherData(

    val cityName: String,

    val date: Long,

    val daily: List<CachedDailyWeatherItem>,
)
