package com.example.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "hourly_weather")
data class HourlyWeatherDataItem(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long? = null,

    @ColumnInfo(name = "date")
    val date: Long,

    @ColumnInfo(name = "temperature")
    val temperature: Int,

    @ColumnInfo(name = "icon")
    val icon: String,

    )