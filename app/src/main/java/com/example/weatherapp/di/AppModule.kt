package com.example.weatherapp.di

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.City
import com.example.domain.models.DayWeatherItem
import com.example.weatherapp.presentation.adapters.DailyWeatherAdapter
import com.example.weatherapp.presentation.adapters.RecentlySearchedCitiesAdapter
import com.example.weatherapp.presentation.fragments.*
import com.example.weatherapp.presentation.viewModels.*
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    fragment { HomeScreenFragment() }
    fragment { DailyForecastFragment() }
    fragment { MainFragment() }
    fragment { SearchScreenFragment() }
    fragment { SettingsScreenFragment() }
    fragment { LoadingScreenFragment() }
    fragment { OnBoardingFragment() }

    viewModel {
        HomeScreenViewModel(
            showCurrentDayWeatherUseCase = get(),
            showTomorrowDayWeatherUseCase = get(),
            getCurrentCityUseCase = get()
        )
    }
    viewModel {
        DailyForecastViewModel(
            showDailyForecastUseCase = get(),
            getCurrentCityUseCase = get()
        )
    }

    viewModel { MainFragmentViewModel() }
    viewModel { OnBoardingViewModel(changeCityUseCase = get()) }

    viewModel {
        SearchScreenViewModel(
            changeCityUseCase = get(),
            getRecentlySearchedCitiesUseCase = get()
        )
    }
    viewModel { SettingsScreenViewModel() }
    viewModel { LoadingScreenViewModel(loadWeatherDataUseCase = get()) }

    single { (list: List<DayWeatherItem>) -> DailyWeatherAdapter(get(), list) }
    single { (list: List<City>) -> RecentlySearchedCitiesAdapter(get(), list) }

    factory(named(KoinConstants.VERTICAL)) {
        LinearLayoutManager(get(), RecyclerView.VERTICAL, false)
    }

    factory(named(KoinConstants.VERTICAL_REVERSED)) {
        LinearLayoutManager(get(), RecyclerView.VERTICAL, true)
    }
}