package com.example.weatherapp.presentation.mainScreens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private val viewModel by viewModel<MainFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val backButton = view.findViewById<ImageView>(R.id.back_button)
        val helpButton = view.findViewById<ImageView>(R.id.help_button)
        val currentScreenTitle = view.findViewById<TextView>(R.id.current_screen_title)
        val bottomNavigationView =
            view.findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)


        helpButton.setOnClickListener {
            Toast.makeText(
                get(),
                "Hello from WeatherApp!",
                Toast.LENGTH_LONG
            ).show()
        }

        viewModel.setupBottomNavigation(bottomNavigationView, childFragmentManager)

        viewModel.currentScreenTitle.observe(viewLifecycleOwner) { currentScreenTitle.text = it }

        backButton.setOnClickListener {
            viewModel.onBackButtonClicked(childFragmentManager)
        }

        return view
    }
}