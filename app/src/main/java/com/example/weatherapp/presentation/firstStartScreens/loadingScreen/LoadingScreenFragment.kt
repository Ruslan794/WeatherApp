package com.example.weatherapp.presentation.firstStartScreens.loadingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadingScreenFragment : Fragment() {

    private val viewModel by viewModel<LoadingScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loading_screen, container, false)

        viewModel.dataIsDownloaded.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(R.id.action_loadingScreenFragment_to_mainFragment)
            else findNavController().navigate(R.id.action_loadingScreenFragment_to_onBoardingFragment)
        }

        return view
    }
}