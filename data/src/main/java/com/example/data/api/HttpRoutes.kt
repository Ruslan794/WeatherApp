package com.example.data.api

object HttpRoutes {

    private const val WEATHER_BASE_URL = "https://api.openweathermap.org"
    private const val CITY_BASE_URL = "https://api.api-ninjas.com"

    const val WEATHER_API_KEY = "0f471001b3c5b773838af3b71748a1d7"
    const val CITY_API_KEY = "ackVNWcjb9iyCWoe8qtnZA==2iNB6een7BhT8IiG"

    const val METRIC_UNITS = "metric"
    const val CURRENT = "current"


    const val WEATHER_API_ROUTE = "$WEATHER_BASE_URL/data/2.5/onecall?"
    const val CITY_API_ROUTE = "$CITY_BASE_URL/v1/city?"
}