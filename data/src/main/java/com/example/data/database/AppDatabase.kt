package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.dao.CityDao
import com.example.data.database.dao.WeatherDao
import com.example.data.database.entities.CityItem
import com.example.data.database.entities.DailyWeatherDataItem
import com.example.data.database.entities.HourlyWeatherDataItem
import com.example.data.database.entities.WeatherData

@Database(
    entities = [CityItem::class, WeatherData::class, DailyWeatherDataItem::class, HourlyWeatherDataItem::class],
    version = 4
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao
}

