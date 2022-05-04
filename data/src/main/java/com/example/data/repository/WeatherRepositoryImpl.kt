package com.example.data.repository

import com.example.data.api.CityApi
import com.example.data.api.WeatherApi
import com.example.data.api.models.CurrentWeather
import com.example.data.database.AppDatabaseHelper
import com.example.data.database.entities.DailyWeatherDataItem
import com.example.data.database.entities.WeatherData
import com.example.data.repository.models.CachedWeatherData
import com.example.domain.models.City
import com.example.domain.models.DayWeatherExtendedItem
import com.example.domain.models.DayWeatherItem
import com.example.domain.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val cityApi: CityApi,
    private val database: AppDatabaseHelper,
    private val dataFormatter: DataFormatter
) :
    WeatherRepository {

    private var cachedWeatherData: CachedWeatherData? = null
    private var cachedCityData: City? = null

    override suspend fun getCurrentDayWeather(city: City): DayWeatherExtendedItem? {

        val weatherData = getWeatherData(city)

        return if (weatherData != null) dataFormatter.convertCachedWeatherDataToDayWeatherExtendedItem(
            weatherData,
            0
        )
        else null
    }

    override suspend fun getTomorrowDayWeather(city: City): DayWeatherExtendedItem? {

        val weatherData = getWeatherData(city)

        return if (weatherData != null) dataFormatter.convertCachedWeatherDataToDayWeatherExtendedItem(
            weatherData,
            1
        )
        else null
    }

    override suspend fun getDailyForecast(city: City): List<DayWeatherItem> {

        val weatherData = getWeatherData(city)

        return if (weatherData != null) dataFormatter.convertCachedWeatherDataToListOfDayWeatherItems(
            weatherData
        )
        else emptyList()
    }


    override suspend fun loadData(): Boolean {
        val cityResponse = getCurrentCity()
        return if (cityResponse != null) {
            getWeatherData(cityResponse)
            true
        } else false
    }

    override suspend fun getCurrentCity(): City? {

        val databaseValue =
            withContext(Dispatchers.IO) { return@withContext database.getCurrentCity() }

        return if (databaseValue != null) {
            val value = City(
                databaseValue.name,
                databaseValue.country,
                databaseValue.latitude,
                databaseValue.longitude
            )
            cachedCityData = value
            value
        } else null
    }

    override suspend fun getCityByName(cityName: String): Int {
        return withContext(Dispatchers.IO) {
            val databaseValue = database.getCityByName(cityName)
            when {
                (cachedCityData?.name?.lowercase() == cityName) -> 1

                (databaseValue?.lowercaseName == cityName) -> {
                    val value = City(
                        databaseValue.name,
                        databaseValue.country,
                        databaseValue.latitude,
                        databaseValue.longitude
                    )
                    withContext(Dispatchers.IO) {
                        database.insertCity(dataFormatter.convertCityToCityItem(value))
                    }
                    cachedCityData = value
                    2
                }

                else -> {
                    val apiData = cityApi.getCityInfoByName(cityName)
                    when {
                        apiData == null -> 0
                        apiData.isEmpty() -> -1
                        else -> {
                            val value = City(
                                apiData[0].name,
                                apiData[0].country,
                                apiData[0].latitude,
                                apiData[0].longitude
                            )
                            withContext(Dispatchers.IO) {
                                database.insertCity(dataFormatter.convertCityToCityItem(value))
                            }
                            cachedCityData = value
                            2
                        }
                    }
                }
            }
        }
    }

    private fun cacheWeatherData(
        dbData: WeatherData? = null,
        apiData: CurrentWeather? = null,
        cityName: String
    ) {
        if (dbData != null && apiData == null) {
            cachedWeatherData = CachedWeatherData(
                cityName,
                dbData.date,
                dataFormatter.convertDailyWeatherDataItemToCachedDailyWeatherItem(dbData.daily)
            )
        } else if (apiData != null && dbData == null) {
            cachedWeatherData = CachedWeatherData(
                cityName,
                apiData.hourly[0].dt,
                dataFormatter.convertCurrentWeatherToCachedDailyWeatherItem(
                    apiData.daily,
                    apiData.hourly
                )
            )
        }
    }

    override suspend fun getRecentlySearchedCities(): List<City> {
        val list: MutableList<City> = mutableListOf()
        withContext(Dispatchers.IO) {
            database.getAllCities().forEach {
                list.add(City(it.name, it.country, it.latitude, it.longitude))
            }
        }
        return list
    }

    private suspend fun getWeatherData(city: City): CachedWeatherData? {
        return withContext(Dispatchers.IO) {
            val databaseValue = database.getWeatherByCityName(cityName = city.name)
            val currentDate: Long = Date().toInstant().epochSecond

            // 1 hour = 3600 seconds
            return@withContext when {

                (cachedWeatherData?.cityName == city.name) -> cachedWeatherData

                (databaseValue != null && (currentDate - databaseValue.date <= 3600)) -> {
                    withContext(Dispatchers.IO) { cacheWeatherData(databaseValue, null, city.name) }
                    CachedWeatherData(
                        city.name,
                        databaseValue.date,
                        dataFormatter.convertDailyWeatherDataItemToCachedDailyWeatherItem(
                            databaseValue.daily
                        )
                    )
                }

                (databaseValue == null || (currentDate - databaseValue.date >= 3600)) -> {
                    val apiData = weatherApi.getWeatherData(
                        dataFormatter.convertCityToCityCoordinates(city)
                    )
                    if (apiData == null) return@withContext null
                    else {
                        withContext(Dispatchers.IO) { cacheWeatherData(null, apiData, city.name) }
                        withContext(Dispatchers.IO) {
                            val list = mutableListOf<DailyWeatherDataItem>()
                            var i = 0
                            apiData.daily.forEach {
                                list.add(
                                    dataFormatter.dailyItemToDailyWeatherDataItem(
                                        it,
                                        apiData.hourly,
                                        i + 1
                                    )
                                )
                                i++
                            }
                            database.insertWeatherData(
                                WeatherData(
                                    null, city.name, apiData.hourly[0].dt, list
                                )
                            )
                        }
                        return@withContext CachedWeatherData(
                            city.name,
                            apiData.hourly[0].dt,
                            dataFormatter.convertCurrentWeatherToCachedDailyWeatherItem(
                                apiData.daily,
                                apiData.hourly
                            )
                        )
                    }
                }
                else -> null
            }
        }
    }
}

