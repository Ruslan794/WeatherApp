package com.example.data.api

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.data.api.models.CityInfo
import io.ktor.client.*
import io.ktor.client.request.*
import java.lang.Exception
import java.net.ConnectException
import java.util.*

class CityApi(private val client: HttpClient) {

    suspend fun getCityInfoByName(cityName: String): List<CityInfo>? {
        return try {
            Log.d("NETWORK_ERROR","TRY")
            client.get {
                url(HttpRoutes.CITY_API_ROUTE)
                header("X-Api-Key", HttpRoutes.CITY_API_KEY)
                parameter("name", cityName.lowercase(Locale.getDefault()))
            }
        } catch (e: Exception) {
           null
        }
    }
}