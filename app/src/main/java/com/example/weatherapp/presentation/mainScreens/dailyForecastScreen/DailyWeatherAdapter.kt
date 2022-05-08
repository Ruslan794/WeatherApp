package com.example.weatherapp.presentation.mainScreens.dailyForecastScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.models.DayWeatherItem
import com.example.weatherapp.R
import com.example.weatherapp.presentation.common.WeatherIconsProvider
import java.text.SimpleDateFormat
import java.util.*


class DailyWeatherAdapter(
    context: Context,
    var weatherList: List<DayWeatherItem?>,
    private val iconsProvider: WeatherIconsProvider
) : RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = weatherList.size

    private fun getItem(position: Int): DayWeatherItem? = weatherList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            inflater.inflate(R.layout.daily_weather_view_holder, parent, false),
            iconsProvider
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!weatherList.contains(null)) holder.bind(getItem(position)!!)
        else holder.bindWithoutData()
    }

    class ViewHolder(view: View, private val iconsProvider: WeatherIconsProvider) :
        RecyclerView.ViewHolder(view) {

        private val temperature: TextView = itemView.findViewById(R.id.temperature)
        private val day: TextView = itemView.findViewById(R.id.day)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val icon: ImageView = itemView.findViewById(R.id.daily_weather_icon)

        fun bind(weatherItem: DayWeatherItem) {
            temperature.text = weatherItem.temperature.toString().plus("\u00B0")
            temperature.background.alpha = 0
            day.text = editDataFormat(weatherItem.date, 0)
            day.background.alpha = 0
            date.text = editDataFormat(weatherItem.date, 1)
            date.background.alpha = 0
            icon.scaleX = 1.8f
            icon.scaleY = 1.8f
            icon.load(iconsProvider.getIconByCode(weatherItem.icon))
        }

        fun bindWithoutData() {}

        private fun editDataFormat(unformattedDate: Long, mode: Int): String {
            return when (mode) {
                0 -> {
                    val simpleDateFormat = SimpleDateFormat("EE", Locale.ENGLISH)
                    simpleDateFormat.format(unformattedDate * 1000L)
                }
                1 -> {
                    val simpleDateFormat = SimpleDateFormat("MMM d", Locale.ENGLISH)
                    val date = simpleDateFormat.format(unformattedDate * 1000L)
                    date.replace('M', 'm')
                }
                else -> {
                    val simpleDateFormat = SimpleDateFormat("EE, MMMM dddd yyyy", Locale.ENGLISH)
                    simpleDateFormat.format(unformattedDate * 1000L)
                }
            }
        }
    }

}
