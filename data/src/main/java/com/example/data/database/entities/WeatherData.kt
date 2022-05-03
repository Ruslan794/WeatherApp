package com.example.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.api.models.DailyItem
import com.example.data.api.models.HourlyItem

@Entity(tableName = "weather")
data class WeatherData(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long? = null,

    @ColumnInfo(name = "city_name")
    val cityName: String,

    @ColumnInfo(name = "date")
    val date: Long,

    @ColumnInfo(name = "daily_weather")
    val daily: List<DailyWeatherDataItem>,

    )