package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.CityItem


@Dao
interface CityDao {

    @Query("SELECT * FROM city_info")
    fun getAll(): List<CityItem>

    @Query("SELECT * FROM city_info ORDER BY id DESC LIMIT 1")
    fun getCurrentCity(): CityItem?

    @Query("SELECT * FROM city_info  WHERE lowercase_name = :name")
    fun getCityByName(name: String): CityItem?

    @Query("DELETE FROM city_info WHERE name = :name")
    fun deleteCityByName(name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: CityItem): Long

}