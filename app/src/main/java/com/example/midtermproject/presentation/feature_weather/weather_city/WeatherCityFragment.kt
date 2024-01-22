package com.example.midtermproject.presentation.feature_weather.weather_city

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midtermproject.R
import com.example.midtermproject.databinding.FragmentWeatherCityLayoutBinding
import com.example.midtermproject.presentation.base.BaseFragment
import com.example.midtermproject.presentation.feature_weather.event.WeatherCityEvent
import com.example.midtermproject.presentation.feature_weather.model.WeatherCityState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherCityFragment : BaseFragment<FragmentWeatherCityLayoutBinding>(FragmentWeatherCityLayoutBinding::inflate) {

    private val weatherCityViewModel : WeatherCityViewModel by viewModels()

    private lateinit var weatherCityAdapter: WeatherCityRecyclerAdapter
    override fun bind() {
        bindRecyclerView()
    }
    override fun bindViewActionListeners() {
        bindBackBtn()
        bindCitySearchBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindCityStateFlow()
    }

    private fun bindRecyclerView() {
        weatherCityAdapter = WeatherCityRecyclerAdapter()
        binding.cityWeatherRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = weatherCityAdapter
        }
    }

    private fun bindCitySearchBtn() {
        with(binding) {
            btnSearch.setOnClickListener {
                weatherCityViewModel.onEvent(WeatherCityEvent.GetWeatherForCity(city = etCitySearch.text.toString()))
            }
        }
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                weatherCityViewModel.onEvent(WeatherCityEvent.NavigateBack)
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherCityViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is WeatherCityNavigationEvent.NavigateBack -> navigateBack()
                        is WeatherCityNavigationEvent.NavigateToItemClicked -> navigateToItemClicked()
                        else -> {}
                    }
                }
            }
        }
    }

    private fun bindCityStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherCityViewModel.weatherCityState.collect { state ->
                    Log.d("WeatherCityStateLog", "Collected state: $state")
                    handleWeatherCityState(state = state)
                }
            }
        }
    }

    private fun handleWeatherCityState(state : WeatherCityState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.detailedWeatherInfo?.let { detailedCityWeather ->
            weatherCityAdapter.submitList(detailedCityWeather)
        }

        state.errorMessage?.let { errorMessage ->
            showErrorScreen(errorMessage)
        }
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_weatherCityFragment_to_weatherTodayFragment)
    }

    private fun showErrorScreen(message : String) {

    }

    private fun navigateToItemClicked() {

    }
}