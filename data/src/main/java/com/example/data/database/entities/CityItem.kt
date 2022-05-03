package com.example.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_info")
data class CityItem(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long? = null,

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "lowercase_name")
    val lowercaseName : String,

    @ColumnInfo(name = "latitude")
    val latitude :Double,

    @ColumnInfo(name = "longitude")
    val longitude : Double,

    @ColumnInfo(name = "country")
    val country : String
)