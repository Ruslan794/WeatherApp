package com.example.weatherapp.app

import android.app.Application
import com.example.weatherapp.di.appModule
import com.example.data.di.dataModule
import com.example.weatherapp.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}