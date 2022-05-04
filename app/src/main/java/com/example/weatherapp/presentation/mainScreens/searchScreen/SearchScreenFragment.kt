package com.example.weatherapp.presentation.mainScreens.searchScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.City
import com.example.weatherapp.R
import com.example.weatherapp.di.KoinConstants
import com.example.weatherapp.presentation.common.CityListClickListener
import com.example.weatherapp.presentation.common.CityAdapter
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SearchScreenFragment : Fragment() {

    private val viewModel by viewModel<SearchScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_screen, container, false)

        val cityDataList = listOf<City?>(null)
        val clickListener = CityListClickListener { viewModel.changeCity(it.name) }
        val adapter: CityAdapter = get { parametersOf(cityDataList, clickListener) }
        val linearLayoutManager: LinearLayoutManager = get(named(KoinConstants.VERTICAL_REVERSED))

        val cityList = view.findViewById<RecyclerView>(R.id.recently_searched_cities)
        val search = view.findViewById<SearchView>(R.id.search)

        search.setOnQueryTextListener(viewModel.searchViewOnQueryTextListener(search))

        cityList.adapter = adapter
        cityList.layoutManager = linearLayoutManager

        viewModel.showCityChangeResultToast.observe(viewLifecycleOwner) {
            val message = when (it) {
                -1 -> "Such city doesn't exist!"
                0 -> "No internet connection!"
                1 -> "This city is already current."
                2 -> "City changed successfully!"
                else -> "Unknown error!"
            }
            Toast.makeText(get(), message, Toast.LENGTH_LONG).show()
        }

        viewModel.cityDataList.observe(viewLifecycleOwner) {
            viewModel.onCityListChanged(it, adapter)
        }

        return view
    }

}