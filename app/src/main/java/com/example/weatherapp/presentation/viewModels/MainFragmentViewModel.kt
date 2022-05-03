package com.example.weatherapp.presentation.viewModels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragmentViewModel : ViewModel() {


    private val _currentScreenTitle = MutableLiveData<String>()
    val currentScreenTitle: LiveData<String> = _currentScreenTitle


    fun onBackButtonClicked(
        childFragmentManager: FragmentManager
    ) {
        val navController =
            (childFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment).navController

        navController.popBackStack()
        _currentScreenTitle.value = when (navController.currentDestination!!.id) {
            R.id.homeScreenFragment -> "Weather"
            R.id.searchScreenFragment -> "Search"
            R.id.dailyForecastFragment -> "Daily Forecast"
            R.id.settingsScreenFragment -> "Settings"
            else -> "Error"
        }
    }

    fun setupBottomNavigation(
        bottomNavigationView: BottomNavigationView,
        childFragmentManager: FragmentManager
    ) {
        val navController =
            (childFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment).navController
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeScreenFragment -> {
                    if (!item.isChecked) navController.navigate(R.id.homeScreenFragment)
                    _currentScreenTitle.value = "Weather"
                    return@setOnItemSelectedListener true
                }
                R.id.searchScreenFragment -> {
                    if (!item.isChecked) navController.navigate(R.id.searchScreenFragment)
                    _currentScreenTitle.value = "Search"
                    return@setOnItemSelectedListener true
                }
                R.id.dailyForecastFragment -> {
                    if (!item.isChecked) navController.navigate(R.id.dailyForecastFragment)
                    _currentScreenTitle.value = "Daily Forecast"
                    return@setOnItemSelectedListener true
                }
                R.id.settingsScreenFragment -> {
                    if (!item.isChecked) navController.navigate(R.id.settingsScreenFragment)
                    _currentScreenTitle.value = "Settings"
                    return@setOnItemSelectedListener true
                }
            }
            _currentScreenTitle.value = "Weather"
            false
        }
    }
}
