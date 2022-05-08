package com.example.weatherapp.presentation.mainScreens.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
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

        viewModel.temperature.observe(viewLifecycleOwner) {
            temperature.text = it; temperature.background.alpha = 0
        }
        viewModel.windSpeed.observe(viewLifecycleOwner) {
            windSpeed.text = it; windSpeed.background.alpha = 0
        }
        viewModel.humidity.observe(viewLifecycleOwner) {
            humidity.text = it; humidity.background.alpha = 0
        }
        viewModel.minTemperature.observe(viewLifecycleOwner) {
            minTemperature.text = it; minTemperature.background.alpha = 0
        }
        viewModel.date.observe(viewLifecycleOwner) { date.text = it; date.background.alpha = 0 }
        viewModel.city.observe(viewLifecycleOwner) {
            city.text = it
            city.background.alpha = 0
            val img =
                ResourcesCompat.getDrawable(resources, R.drawable.place_icon, resources.newTheme())
            img?.setBounds(0, 0, 60, 60)
            city.setCompoundDrawables(img, null, null, null)
        }
        viewModel.weatherInOneWord.observe(viewLifecycleOwner) { weatherInOneWord.text = it }
        viewModel.icon.observe(viewLifecycleOwner) {
            icon.load("https://openweathermap.org/img/wn/${it}@2x.png")
            icon.scaleX = 1.8f
            icon.scaleY = 1.8f
        }



        viewModel.hourlyTemperatureFirst.observe(viewLifecycleOwner) {
            hourlyTemperatureFirst.text = it; hourlyTemperatureFirst.background.alpha = 0
        }
        viewModel.hourlyTemperatureSecond.observe(viewLifecycleOwner) {
            hourlyTemperatureSecond.text = it; hourlyTemperatureSecond.background.alpha = 0
        }
        viewModel.hourlyTemperatureThird.observe(viewLifecycleOwner) {
            hourlyTemperatureThird.text = it; hourlyTemperatureThird.background.alpha = 0
        }
        viewModel.hourlyTimeFirst.observe(viewLifecycleOwner) {
            hourlyTimeOne.text = it; hourlyTimeOne.background.alpha = 0
        }
        viewModel.hourlyTimeSecond.observe(viewLifecycleOwner) {
            hourlyTimeSecond.text = it; hourlyTimeSecond.background.alpha = 0
        }
        viewModel.hourlyTimeThird.observe(viewLifecycleOwner) {
            hourlyTimeThird.text = it; hourlyTimeThird.background.alpha = 0
        }
        viewModel.hourlyIconOne.observe(viewLifecycleOwner) {
            loadImageToHourlyItem(hourlyIconOne, it)
        }
        viewModel.hourlyIconTwo.observe(viewLifecycleOwner) {
            loadImageToHourlyItem(hourlyIconTwo, it)
        }
        viewModel.hourlyIconThree.observe(viewLifecycleOwner) {
            loadImageToHourlyItem(hourlyIconThree, it)
        }
        viewModel.showConnectionErrorToast.observe(viewLifecycleOwner) {
            Toast.makeText(
                get(),
                "Failed to update data! Check your internet connection!",
                Toast.LENGTH_LONG
            ).show()
        }

        return view
    }

    private fun loadImageToHourlyItem(view: ImageView, path: String) {
        view.scaleType = ImageView.ScaleType.CENTER_CROP
        view.scaleX = 1.3f
        view.scaleY = 1.3f
        view.load(viewModel.getIcon(path))
    }
}

