package com.example.midtermproject.presentation.feature_weather.weather_today

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.midtermproject.R
import com.example.midtermproject.databinding.FragmentWeatherTodayLayoutBinding
import com.example.midtermproject.presentation.base.BaseFragment
import com.example.midtermproject.presentation.feature_weather.event.WeatherTodayEvent
import com.example.midtermproject.presentation.feature_weather.model.WeatherDayDetails
import com.example.midtermproject.presentation.feature_weather.model.WeatherDayState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherTodayFragment : BaseFragment<FragmentWeatherTodayLayoutBinding>(FragmentWeatherTodayLayoutBinding::inflate) {

    private val weatherTodayViewModel : WeatherTodayViewModel by viewModels()

    override fun bindViewActionListeners() {
        bindLogoutBtn()
        bindDetailsBtn()
        bindCityNavigationButton()
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

    private fun bindCityNavigationButton() {
        with(binding) {
            btnCitySearch.setOnClickListener {
                weatherTodayViewModel.onEvent(WeatherTodayEvent.NavigateToCityWeather)
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
                        is WeatherNavigationEvent.NavigateToCityWeather -> navigateToCityWeather()
                        else -> {}
                    }
                }
            }
        }
    }

    private fun handleWeatherState(state: WeatherDayState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.detailedWeatherInfo?.firstOrNull()?.let { detailedWeather ->
            updateWeatherUI(detailedWeather)
        }

        state.errorMessage?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateWeatherUI(weather: WeatherDayDetails) {
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
    private fun navigateToCityWeather() {
        findNavController().navigate(R.id.action_weatherTodayFragment_to_weatherCityFragment)
    }
}