package com.example.data.database

import androidx.room.TypeConverter
import com.example.data.database.entities.DailyWeatherDataItem
import com.example.data.database.entities.HourlyWeatherDataItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converters {
    @TypeConverter
    fun dailyWeatherFromList(value: List<DailyWeatherDataItem>) = Json.encodeToString(value)

    @TypeConverter
    fun dailyWeatherToList(value: String) = Json.decodeFromString<List<DailyWeatherDataItem>>(value)

    @TypeConverter
    fun hourlyWeatherFromList(value: List<HourlyWeatherDataItem>) = Json.encodeToString(value)

    @TypeConverter
    fun hourlyWeatherToList(value: String) = Json.decodeFromString<List<HourlyWeatherDataItem>>(value)
}