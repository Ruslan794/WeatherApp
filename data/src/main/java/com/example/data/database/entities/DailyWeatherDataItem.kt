package com.example.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "daily_weather")
data class DailyWeatherDataItem(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long? = null,

    @ColumnInfo(name = "date")
    val date: Long,

    @ColumnInfo(name = "min_temperature")
    val MinTemperature: Int,

    @ColumnInfo(name = "eve_temperature")
    val EveTemperature: Int,

    @ColumnInfo(name = "humidity")
    val humidity: Int,

    @ColumnInfo(name = "wind_speed")
    val windSpeed: Int,

    @ColumnInfo(name = "icon")
    val icon: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "hourly_weather")
    val hourlyWeather: List<HourlyWeatherDataItem>,

    )