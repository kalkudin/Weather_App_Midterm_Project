package com.example.midtermproject.weather_feature.presentation.map

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.midtermproject.R
import com.example.midtermproject.auth_feature.presentation.base.BaseFragment
import com.example.midtermproject.databinding.FragmentWeatherTodayLayoutBinding
import com.example.midtermproject.weather_feature.presentation.model.WeatherDetailedInfo
import com.example.midtermproject.weather_feature.presentation.model.WeatherState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class WeatherTodayFragment : BaseFragment<FragmentWeatherTodayLayoutBinding>(FragmentWeatherTodayLayoutBinding::inflate) {

    private val weatherTodayViewModel : WeatherTodayViewModel by viewModels()

    override fun bind() {
    }

    override fun bindViewActionListeners() {
        bindLogoutBtn()
    }

    override fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherTodayViewModel.weatherState.collect { state ->
                    handleWeatherState(state)
                }
            }
        }
        bindNavigationEvent()
    }

    private fun bindLogoutBtn() {
        with(binding) {
            btnLogout.setOnClickListener {
                weatherTodayViewModel.onEvent(WeatherTodayEvent.LogOut)
            }
        }
    }

    private fun bindNavigationEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                weatherTodayViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is WeatherNavigationEvent.NavigateToHome -> navigateToHome()
                    }
                }
            }
        }
    }

    private fun handleWeatherState(state: WeatherState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.detailedWeatherInfo?.getOrNull(2)?.let { detailedWeather ->
            updateWeatherUI(detailedWeather)
        }

        state.errorMessage?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateWeatherUI(weather: WeatherDetailedInfo) {
        val formattedTime = weather.time.format(DateTimeFormatter.ofPattern("HH:mm"))
        val formattedTemp = "${weather.temperatureCelsius}°C"

        binding.apply {
            iconPrimary.setImageResource(weather.iconRes)
            desc.text = weather.weatherDescription
            time.text = "Time: $formattedTime"
            humidity.text = "Humidity: ${weather.relativeHumidity}%"
            temp.text = "Temp: $formattedTemp"
            speed.text = "Wind Speed: ${weather.windSpeed} km/h"
            humidityProgressBar.progress = weather.relativeHumidity
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_weatherTodayFragment_to_homeFragment)
    }
}