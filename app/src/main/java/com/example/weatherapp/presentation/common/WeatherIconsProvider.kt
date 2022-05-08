package com.example.weatherapp.presentation.common

import com.example.weatherapp.R

class WeatherIconsProvider {
    fun getIconByCode(code: String): Int {
        return when (code) {
            "01d" -> R.drawable.ic_weather_01d
            "02d" -> R.drawable.ic_weather_02d
            "03d", "03n" -> R.drawable.ic_weather_03d
            "04d", "04n" -> R.drawable.ic_weather_04d
            "09d" -> R.drawable.ic_weather_09d
            "10d" -> R.drawable.ic_weather_10d
            "11d" -> R.drawable.ic_weather_11d
            "13d" -> R.drawable.ic_weather_13d
            "50d" -> R.drawable.ic_weather_50d
            "01n" -> R.drawable.ic_weather_01n
            "02n" -> R.drawable.ic_weather_02n
            else -> R.drawable.ic_help_outline
        }
    }
}