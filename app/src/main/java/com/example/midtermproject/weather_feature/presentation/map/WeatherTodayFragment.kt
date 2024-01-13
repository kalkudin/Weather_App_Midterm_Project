package com.example.midtermproject.weather_feature.presentation.map

import androidx.fragment.app.viewModels
import com.example.midtermproject.auth_feature.presentation.base.BaseFragment
import com.example.midtermproject.databinding.FragmentWeatherTodayLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherTodayFragment : BaseFragment<FragmentWeatherTodayLayoutBinding>(FragmentWeatherTodayLayoutBinding::inflate) {

    private val weatherTodayViewModel : WeatherTodayViewModel by viewModels()

    override fun bind() {
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObservers() {
    }
}