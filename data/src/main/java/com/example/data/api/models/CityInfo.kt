package com.example.data.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CityInfo(
    val name : String,
    val latitude :Double,
    val longitude : Double,
    val country : String
)
