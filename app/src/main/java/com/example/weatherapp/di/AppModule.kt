package com.example.weatherapp.di

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.City
import com.example.domain.models.DayWeatherItem
import com.example.weatherapp.presentation.common.CityAdapter
import com.example.weatherapp.presentation.common.CityListClickListener
import com.example.weatherapp.presentation.common.WeatherIconsProvider
import com.example.weatherapp.presentation.loadingScreen.LoadingScreenFragment
import com.example.weatherapp.presentation.loadingScreen.LoadingScreenViewModel
import com.example.weatherapp.presentation.mainScreens.MainFragment
import com.example.weatherapp.presentation.mainScreens.MainFragmentViewModel
import com.example.weatherapp.presentation.mainScreens.dailyForecastScreen.DailyForecastFragment
import com.example.weatherapp.presentation.mainScreens.dailyForecastScreen.DailyForecastViewModel
import com.example.weatherapp.presentation.mainScreens.dailyForecastScreen.DailyWeatherAdapter
import com.example.weatherapp.presentation.mainScreens.homeScreen.HomeScreenFragment
import com.example.weatherapp.presentation.mainScreens.homeScreen.HomeScreenViewModel
import com.example.weatherapp.presentation.mainScreens.searchScreen.SearchScreenFragment
import com.example.weatherapp.presentation.mainScreens.searchScreen.SearchScreenViewModel
import com.example.weatherapp.presentation.onBoarding.OnBoardingFragment
import com.example.weatherapp.presentation.onBoarding.OnBoardingViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    fragment { HomeScreenFragment() }
    fragment { DailyForecastFragment() }
    fragment { MainFragment() }
    fragment { SearchScreenFragment() }
    fragment { LoadingScreenFragment() }
    fragment { OnBoardingFragment() }

    viewModel {
        HomeScreenViewModel(
            showCurrentDayWeatherUseCase = get(),
            showTomorrowDayWeatherUseCase = get(),
            getCurrentCityUseCase = get(),
            iconsProvider = get()
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
    viewModel { LoadingScreenViewModel(loadWeatherDataUseCase = get()) }

    single { (list: List<DayWeatherItem>) ->
        DailyWeatherAdapter(
            context = get(),
            weatherList = list,
            iconsProvider = get()
        )
    }
    factory { (list: List<City>, onClickListener: CityListClickListener) ->
        CityAdapter(
            context = get(),
            cityList = list,
            clickListener = onClickListener
        )
    }

    factory(named(KoinConstants.VERTICAL)) {
        LinearLayoutManager(get(), RecyclerView.VERTICAL, false)
    }

    factory(named(KoinConstants.VERTICAL_REVERSED)) {
        LinearLayoutManager(get(), RecyclerView.VERTICAL, true)
    }

    factory { WeatherIconsProvider() }
}