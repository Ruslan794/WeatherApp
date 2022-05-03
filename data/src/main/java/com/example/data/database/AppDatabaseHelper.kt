package com.example.data.database

import com.example.data.database.entities.CityItem
import com.example.data.database.entities.WeatherData

class AppDatabaseHelper(private val db: AppDatabase) {

    fun insertCity(city: CityItem) {
        getAllCities().forEach {
            if (it.name == city.name) {
                deleteCityByName(it.name)
                return@forEach
            }
        }
        db.cityDao().insert(city)
    }

    fun getAllCities() = db.cityDao().getAll()

    fun deleteCityByName(name: String) = db.cityDao().deleteCityByName(name)

    fun getCurrentCity(): CityItem? = db.cityDao().getCurrentCity()

    fun getCityByName(name: String): CityItem? = db.cityDao().getCityByName(name)

    fun getWeatherByCityName(cityName: String): WeatherData? =
        db.weatherDao().getWeatherByCityName(cityName)

    fun insertWeatherData(data: WeatherData) = db.weatherDao().insertWeatherData(data)
}