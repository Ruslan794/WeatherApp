package com.example.data.di

import androidx.room.Room
import com.example.data.api.CityApi
import com.example.data.api.WeatherApi
import com.example.data.database.AppDatabase
import com.example.data.database.AppDatabaseHelper
import com.example.data.repository.DataFormatter
import com.example.data.repository.WeatherRepositoryImpl
import com.example.domain.repositories.WeatherRepository
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.koin.dsl.module

val dataModule = module {

    single {
        HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    single {
        WeatherApi(client = get())
    }

    single {
        CityApi(client = get())
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            weatherApi = get(),
            cityApi = get(),
            database = get(),
            dataFormatter = get()
        )
    }

    single { DataFormatter() }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "App.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single {
        AppDatabaseHelper(get())
    }
}