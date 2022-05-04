package com.example.weatherapp.presentation.firstStartScreens.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

class OnBoardingFragment : Fragment() {

    private val viewModel by viewModel<OnBoardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)

        val cityDataList = listOf<City?>(null)
        val clickListener =
            CityListClickListener { viewModel.changeCity(it.name) }
        val adapter: CityAdapter = get { parametersOf(cityDataList, clickListener) }
        val linearLayoutManager: LinearLayoutManager = get(named(KoinConstants.VERTICAL))

        val search = view.findViewById<SearchView>(R.id.search)
        val cityList = view.findViewById<RecyclerView>(R.id.list_of_cities)

        cityList.adapter = adapter
        cityList.layoutManager = linearLayoutManager

        search.setOnQueryTextListener(viewModel.searchViewOnQueryTextListener(search))

        viewModel.showCityChangeResultToast.observe(viewLifecycleOwner) {
            val message = when (it) {
                -1 -> "Such city doesn't exist!"
                1, 2 -> {
                    findNavController().navigate(R.id.action_onBoardingFragment_to_loadingScreenFragment)
                    "Success!"
                }
                0 -> "No internet connection!"
                else -> "Unknown error!"
            }
            Toast.makeText(get(), message, Toast.LENGTH_SHORT).show()
        }

        viewModel.cityList.observe(viewLifecycleOwner) {
            viewModel.onCityListChanged(it, adapter)
        }

        return view
    }
}