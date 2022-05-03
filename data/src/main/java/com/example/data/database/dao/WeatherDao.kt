package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.WeatherData


@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    fun getAllWeatherItem(): List<WeatherData>

//    @Query("SELECT * FROM weather ORDER BY id DESC LIMIT 1")
//    fun getLastWeatherData(): WeatherData?

    @Query("SELECT * FROM weather WHERE city_name=:cityName ORDER BY id DESC LIMIT 1")
    fun getWeatherByCityName(cityName: String): WeatherData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(data: WeatherData): Long

    @Query("DELETE FROM weather WHERE id = :id")
    fun deleteWeatherItemById(id: Long)
}