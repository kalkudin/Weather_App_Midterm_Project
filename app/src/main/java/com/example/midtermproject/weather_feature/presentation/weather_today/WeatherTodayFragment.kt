package com.example.midtermproject.weather_feature.presentation.weather_today

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
import com.example.midtermproject.weather_feature.presentation.event.WeatherTodayEvent
import com.example.midtermproject.weather_feature.presentation.model.WeatherTodayDetails
import com.example.midtermproject.weather_feature.presentation.model.WeatherTodayState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherTodayFragment : BaseFragment<FragmentWeatherTodayLayoutBinding>(FragmentWeatherTodayLayoutBinding::inflate) {

    private val weatherTodayViewModel : WeatherTodayViewModel by viewModels()

    override fun bindViewActionListeners() {
        bindLogoutBtn()
        bindDetailsBtn()
    }

    override fun bindObservers() {
        bindWeatherFlow()
        bindNavigationEvent()
    }

    private fun bindLogoutBtn() {
        with(binding) {
            btnLogout.setOnClickListener {
                weatherTodayViewModel.onEvent(WeatherTodayEvent.LogOut)
            }
        }
    }

    private fun bindDetailsBtn() {
        with(binding) {
            btnNextPage.setOnClickListener {
                weatherTodayViewModel.onEvent(WeatherTodayEvent.MoveToDetailsPage)
            }
        }
    }

    private fun bindWeatherFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherTodayViewModel.weatherState.collect { state ->
                    handleWeatherState(state)
                }
            }
        }
    }

    private fun bindNavigationEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                weatherTodayViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is WeatherNavigationEvent.NavigateToHome -> navigateToHome()
                        is WeatherNavigationEvent.NavigateToWeeklyWeather -> navigateToWeeklyWeather()
                    }
                }
            }
        }
    }

    private fun handleWeatherState(state: WeatherTodayState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.detailedWeatherInfo?.firstOrNull()?.let { detailedWeather ->
            updateWeatherUI(detailedWeather)
        }

        state.errorMessage?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateWeatherUI(weather: WeatherTodayDetails) {
        with(binding) {
            iconPrimary.setImageResource(weather.iconRes)
            tvToday.text = weather.formattedDate
            tvDate.text = weather.formattedTime
            tvTemp.text = "${weather.temperatureCelsius}°C"
            tvDesc.text = weather.weatherDescription
            tvHumValue.text = "${weather.relativeHumidity}%"
            tvWindValue.text = "${weather.windSpeed} km/h"
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_weatherTodayFragment_to_homeFragment)
    }

    private fun navigateToWeeklyWeather() {
        findNavController().navigate(R.id.action_weatherTodayFragment_to_weatherWeeklyFragment)
    }
}