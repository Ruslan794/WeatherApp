package com.example.weatherapp.presentation.mainScreens.dailyForecastScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.domain.models.DayWeatherItem
import com.example.weatherapp.R
import com.example.weatherapp.di.KoinConstants
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


class DailyForecastFragment : Fragment() {

    private val viewModel by viewModel<DailyForecastViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_daily_forecast, container, false)

        val weatherDataList = listOf<DayWeatherItem?>(null,null,null,null,null,null,null)
        val adapter: DailyWeatherAdapter = get { parametersOf(weatherDataList) }
        val linearLayoutManager: LinearLayoutManager = get(named(KoinConstants.VERTICAL))

        val dailyWeatherList = view.findViewById<RecyclerView>(R.id.daily_weather_list)

        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        swipeRefresh.setOnRefreshListener { viewModel.onRefresh(swipeRefresh) }

        viewModel.weatherDataList.observe(viewLifecycleOwner) {
            viewModel.onWeatherChanged(it, adapter)
        }

        viewModel.showConnectionErrorToast.observe(viewLifecycleOwner) {
            Toast.makeText(
                get(),
                "Failed to update data! Check your internet connection!",
                Toast.LENGTH_LONG
            ).show()
        }

        dailyWeatherList.adapter = adapter
        dailyWeatherList.layoutManager = linearLayoutManager
        dailyWeatherList.setHasFixedSize(true)

        return view
    }
}