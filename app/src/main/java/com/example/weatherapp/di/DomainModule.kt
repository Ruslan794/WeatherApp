package com.example.weatherapp.di

import com.example.domain.useCases.*
import org.koin.dsl.module

val domainModule = module {

    factory {
        ShowCurrentDayWeatherUseCase(weatherRepository = get())
    }
    factory {
        ShowTomorrowDayWeatherUseCase(weatherRepository = get())
    }
    factory {
        ShowDailyForecastUseCase(weatherRepository = get())
    }
    factory {
        ChangeCityUseCase(weatherRepository = get())
    }
    factory {
        GetCurrentCityUseCase(weatherRepository = get())
    }
    factory {
        GetRecentlySearchedCitiesUseCase(weatherRepository = get())
    }
    factory {
        LoadWeatherDataUseCase(weatherRepository = get())
    }
}
