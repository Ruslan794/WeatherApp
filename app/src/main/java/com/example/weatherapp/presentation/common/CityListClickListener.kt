package com.example.weatherapp.presentation.common

import com.example.domain.models.City

class CityListClickListener(val onItemClicked: (city : City) -> Unit) {
    fun onClick(city : City) = onItemClicked(city)
}