package com.example.data.repository

import com.example.data.api.models.DailyItem
import com.example.data.api.models.HourlyItem
import com.example.data.database.entities.CityItem
import com.example.data.database.entities.DailyWeatherDataItem
import com.example.data.database.entities.HourlyWeatherDataItem
import com.example.data.repository.models.CachedDailyWeatherItem
import com.example.data.repository.models.CachedHourlyWeatherItem
import com.example.data.repository.models.CachedWeatherData
import com.example.domain.models.City
import com.example.domain.models.DayWeatherExtendedItem
import com.example.domain.models.DayWeatherItem
import com.example.domain.models.HourlyWeatherItem

class DataFormatter {

    fun convertCityToCityCoordinates(city: City): com.example.data.api.models.CityInfo {
        return com.example.data.api.models.CityInfo(
            city.name,
            city.latitude,
            city.longitude,
            city.country
        )
    }

    fun dailyItemToDailyWeatherDataItem(
        item: DailyItem,
        hourlyWeather: List<HourlyItem>,
        dayPosition: Int
    ): DailyWeatherDataItem {
        val x = when (dayPosition) {
            1 -> 0
            2 -> 24
            else -> 0
        }
        return DailyWeatherDataItem(
            null,
            item.dt,
            item.temp.min.toInt(),
            item.temp.eve.toInt(),
            item.humidity,
            item.windSpeed.toInt(),
            item.weather[0].icon,
            item.weather[0].main,
            listOf(
                HourlyWeatherDataItem(
                    null,
                    hourlyWeather[x].dt,
                    hourlyWeather[x].temp.toInt(),
                    hourlyWeather[x].weather[0].icon
                ),
                HourlyWeatherDataItem(
                    null,
                    hourlyWeather[x + 1].dt,
                    hourlyWeather[x + 1].temp.toInt(),
                    hourlyWeather[x + 1].weather[0].icon
                ),
                HourlyWeatherDataItem(
                    null,
                    hourlyWeather[x + 2].dt,
                    hourlyWeather[x + 2].temp.toInt(),
                    hourlyWeather[x + 2].weather[0].icon
                )
            )

        )
    }

    fun convertDailyWeatherDataItemToCachedDailyWeatherItem(data: List<DailyWeatherDataItem>): List<CachedDailyWeatherItem> {
        val list = mutableListOf<CachedDailyWeatherItem>()
        data.forEach { it ->
            val sublist = mutableListOf<CachedHourlyWeatherItem>()
            it.hourlyWeather.forEach {
                sublist.add(
                    CachedHourlyWeatherItem(
                        it.date,
                        it.temperature,
                        it.icon
                    )
                )
            }
            list.add(
                CachedDailyWeatherItem(
                    it.date,
                    it.MinTemperature,
                    it.EveTemperature,
                    it.humidity,
                    it.windSpeed,
                    it.icon,
                    it.description,
                    sublist
                )
            )
        }
        return list
    }

    fun convertCurrentWeatherToCachedDailyWeatherItem(
        dailyList: List<DailyItem>,
        hourlyList: List<HourlyItem>,

        ): List<CachedDailyWeatherItem> {
        val list = mutableListOf<CachedDailyWeatherItem>()
        var i = 3
        dailyList.forEach {
            val sublist = mutableListOf<CachedHourlyWeatherItem>()
            hourlyList.subList(i - 3, i).forEach { item ->
                sublist.add(
                    CachedHourlyWeatherItem(
                        item.dt,
                        item.temp.toInt(),
                        item.weather[0].icon
                    )
                )
            }
            list.add(
                CachedDailyWeatherItem(
                    it.dt,
                    it.temp.min.toInt(),
                    it.temp.eve.toInt(),
                    it.humidity,
                    it.windSpeed.toInt(),
                    it.weather[0].icon,
                    it.weather[0].main,
                    sublist
                )
            )
            i += 3
        }
        return list
    }

    fun convertCachedWeatherDataToDayWeatherExtendedItem(
        data: CachedWeatherData,
        dayNumber: Int,
    ): DayWeatherExtendedItem {
        val day = data.daily[dayNumber]

        val hourlyWeatherList = mutableListOf<HourlyWeatherItem>()
        day.hourlyWeather.forEach {
            hourlyWeatherList.add(
                HourlyWeatherItem(
                    it.date,
                    it.temperature,
                    it.icon
                )
            )
        }

        return DayWeatherExtendedItem(
            day.date,
            day.eveTemperature,
            day.windSpeed,
            day.humidity,
            day.minTemperature,
            day.icon,
            day.description,
            hourlyWeatherList
        )
    }

    fun convertCachedWeatherDataToListOfDayWeatherItems(
        data: CachedWeatherData
    ): List<DayWeatherItem> {
        val list: MutableList<DayWeatherItem> = mutableListOf()
        data.daily.subList(0,7).forEach {
            list.add(
                DayWeatherItem(
                    it.date,
                    it.eveTemperature,
                    it.icon
                )
            )
        }
        return list
    }

    fun convertCityToCityItem(city: City): CityItem =
        CityItem(
            null,
            city.name,
            city.name.lowercase(),
            city.latitude,
            city.longitude,
            city.country
        )


}