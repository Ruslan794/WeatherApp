package com.example.weatherapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.presentation.viewModels.SettingsScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsScreenFragment : Fragment() {

    private val viewModel by viewModel<SettingsScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_screen, container, false)
        return view
    }

}