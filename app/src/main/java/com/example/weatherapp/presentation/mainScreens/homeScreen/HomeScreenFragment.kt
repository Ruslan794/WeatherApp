package com.example.weatherapp.presentation.mainScreens.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import com.example.weatherapp.R
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeScreenFragment : Fragment() {

    private val viewModel by viewModel<HomeScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)

        val date = view.findViewById<TextView>(R.id.date)
        val temperature = view.findViewById<TextView>(R.id.temperature)
        val windSpeed = view.findViewById<TextView>(R.id.wind_speed)
        val humidity = view.findViewById<TextView>(R.id.humidity)
        val minTemperature = view.findViewById<TextView>(R.id.min_temperature)
        val city = view.findViewById<TextView>(R.id.city)
        val weatherInOneWord = view.findViewById<TextView>(R.id.weather_in_one_word)
        val icon = view.findViewById<ImageView>(R.id.main_icon)

        val hourlyTemperatureFirst = view.findViewById<TextView>(R.id.hourly_temp_1)
        val hourlyTemperatureSecond = view.findViewById<TextView>(R.id.hourly_temp_2)
        val hourlyTemperatureThird = view.findViewById<TextView>(R.id.hourly_temp_3)
        val hourlyTimeOne = view.findViewById<TextView>(R.id.hourly_time_1)
        val hourlyTimeSecond = view.findViewById<TextView>(R.id.hourly_time_2)
        val hourlyTimeThird = view.findViewById<TextView>(R.id.hourly_time_3)
        val hourlyIconOne = view.findViewById<ImageView>(R.id.hourly_icon_1)
        val hourlyIconTwo = view.findViewById<ImageView>(R.id.hourly_icon_2)
        val hourlyIconThree = view.findViewById<ImageView>(R.id.hourly_icon_3)


        val todayTab = view.findViewById<RadioButton>(R.id.today_tab)
        val tomorrowTab = view.findViewById<RadioButton>(R.id.tomorrow_tab)

        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        swipeRefresh.setOnRefreshListener { viewModel.onRefresh(swipeRefresh) }

        viewModel.todayTabActive.observe(viewLifecycleOwner) { todayTab.isChecked = it }
        viewModel.tomorrowTabActive.observe(viewLifecycleOwner) { tomorrowTab.isChecked = it }

        todayTab.setOnClickListener { viewModel.onTodayTabClicked() }
        tomorrowTab.setOnClickListener { viewModel.onTomorrowTabClicked() }

        viewModel.date.observe(viewLifecycleOwner) { date.text = it }
        viewModel.temperature.observe(viewLifecycleOwner) { temperature.text = "$it" + "\u00B0" }
        viewModel.windSpeed.observe(viewLifecycleOwner) { windSpeed.text = "$it km/h" }
        viewModel.humidity.observe(viewLifecycleOwner) { humidity.text = "$it%" }
        viewModel.minTemperature.observe(viewLifecycleOwner) {
            minTemperature.text = "$it" + "\u00B0"
        }
        viewModel.date.observe(viewLifecycleOwner) { date.text = it }
        viewModel.city.observe(viewLifecycleOwner) { city.text = it }
        viewModel.weatherInOneWord.observe(viewLifecycleOwner) { weatherInOneWord.text = it }
        viewModel.icon.observe(viewLifecycleOwner) { icon.load("https://openweathermap.org/img/wn/${it}@2x.png") }



        viewModel.hourlyTemperatureFirst.observe(viewLifecycleOwner) {
            hourlyTemperatureFirst.text = it + "\u00B0"
        }
        viewModel.hourlyTemperatureSecond.observe(viewLifecycleOwner) {
            hourlyTemperatureSecond.text = it + "\u00B0"
        }
        viewModel.hourlyTemperatureThird.observe(viewLifecycleOwner) {
            hourlyTemperatureThird.text = it + "\u00B0"
        }
        viewModel.hourlyTimeFirst.observe(viewLifecycleOwner) { hourlyTimeOne.text = it }
        viewModel.hourlyTimeSecond.observe(viewLifecycleOwner) { hourlyTimeSecond.text = it }
        viewModel.hourlyTimeThird.observe(viewLifecycleOwner) { hourlyTimeThird.text = it }
        viewModel.hourlyIconOne.observe(viewLifecycleOwner) { hourlyIconOne.load("https://openweathermap.org/img/wn/${it}@2x.png") }
        viewModel.hourlyIconTwo.observe(viewLifecycleOwner) { hourlyIconTwo.load("https://openweathermap.org/img/wn/${it}@2x.png") }
        viewModel.hourlyIconThree.observe(viewLifecycleOwner) { hourlyIconThree.load("https://openweathermap.org/img/wn/${it}@2x.png") }

        viewModel.showConnectionErrorToast.observe(viewLifecycleOwner) {
            Toast.makeText(
                get(),
                "Failed to update data! Check your internet connection!",
                Toast.LENGTH_LONG
            ).show()
        }

        return view
    }
}

