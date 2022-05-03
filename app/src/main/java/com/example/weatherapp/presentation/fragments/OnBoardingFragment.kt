package com.example.weatherapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.presentation.viewModels.OnBoardingViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingFragment : Fragment() {

    private val viewModel by viewModel<OnBoardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)

        val search = view.findViewById<SearchView>(R.id.search)

        search.setOnQueryTextListener(viewModel.searchViewOnQueryTextListener(search))

        viewModel.showCityChangeResultToast.observe(viewLifecycleOwner) {
            val message = when (it) {
                0, 1 -> {
                    findNavController().navigate(R.id.action_onBoardingFragment_to_loadingScreenFragment)
                    "Success!"
                }
                else -> "Error! Check your spelling and internet connection!"
            }
            Toast.makeText(get(), message, Toast.LENGTH_SHORT).show()
        }
        return view
    }
}