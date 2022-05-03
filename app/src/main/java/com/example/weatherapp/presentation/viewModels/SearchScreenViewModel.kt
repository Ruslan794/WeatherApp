package com.example.weatherapp.presentation.viewModels

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.City
import com.example.domain.useCases.ChangeCityUseCase
import com.example.domain.useCases.GetRecentlySearchedCitiesUseCase
import com.example.weatherapp.presentation.adapters.RecentlySearchedCitiesAdapter
import kotlinx.coroutines.launch


class SearchScreenViewModel(
    private val changeCityUseCase: ChangeCityUseCase,
    private val getRecentlySearchedCitiesUseCase: GetRecentlySearchedCitiesUseCase
) : ViewModel() {

    private val _showCityChangeResultToast = MutableLiveData<Int>()
    val showCityChangeResultToast: LiveData<Int> = _showCityChangeResultToast

    private val _cityDataList = MutableLiveData<List<City?>>()
    val cityDataList: LiveData<List<City?>> = _cityDataList

    init {
        viewModelScope.launch {
            updateData()
        }
    }

    fun changeCity(cityName: String) {
        viewModelScope.launch {
            _showCityChangeResultToast.value = when (changeCityUseCase.execute(cityName)) {
                0 -> 0
                1 -> 1
                else -> -1
            }

            updateData()
        }
    }

    fun searchViewOnQueryTextListener(searchView: SearchView): SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    changeCity(query.removePrefix(" ").removeSuffix(" ").lowercase())
                    searchView.setQuery("", false)
                    searchView.clearFocus()
                    return false
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        }

    private fun updateData() {
        viewModelScope.launch {
            getRecentlySearchedCitiesUseCase.execute().let {
                if (it != null) _cityDataList.value = it
                else return@let
            }
        }
    }

    fun onCityListChanged(list: List<City?>, adapter: RecentlySearchedCitiesAdapter) {
        adapter.cityList = list
        adapter.notifyDataSetChanged()
    }
}