package com.example.data.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Temperature(
    val min: Double,
    val eve: Double,
)
