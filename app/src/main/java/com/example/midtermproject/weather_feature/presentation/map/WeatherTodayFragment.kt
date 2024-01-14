package com.example.midtermproject.weather_feature.presentation.map

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
import com.example.midtermproject.weather_feature.presentation.map.model.WeatherState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        state.weatherInfo?.let { weatherInfo ->
            if (weatherInfo.currentWeatherData.isNotEmpty()) {
                val firstWeatherData = weatherInfo.currentWeatherData.first()
                val message = "Time: ${firstWeatherData.time}, Temp: ${firstWeatherData.temperatureCelsius}°C"
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }

        state.errorMessage?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_weatherTodayFragment_to_homeFragment)
    }
}