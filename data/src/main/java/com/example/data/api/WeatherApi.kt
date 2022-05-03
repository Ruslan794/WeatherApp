package com.example.data.api

import com.example.data.api.models.CityInfo
import com.example.data.api.models.CurrentWeather
import io.ktor.client.*
import io.ktor.client.request.*

class WeatherApi(private val client: HttpClient) {

    suspend fun getWeatherData(cityInfo: CityInfo): CurrentWeather? {
        return try {
            client.get {
                url(HttpRoutes.WEATHER_API_ROUTE)
                parameter("lat", cityInfo.latitude)
                parameter("lon", cityInfo.longitude)
                parameter("exclude", HttpRoutes.CURRENT)
                parameter("units", HttpRoutes.METRIC_UNITS)
                parameter("appid", HttpRoutes.WEATHER_API_KEY)
            }
        } catch (e: Exception) {
            null
        }
    }
}
