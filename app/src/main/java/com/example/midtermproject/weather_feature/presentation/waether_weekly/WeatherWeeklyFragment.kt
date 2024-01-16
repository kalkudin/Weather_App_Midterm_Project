package com.example.midtermproject.weather_feature.presentation.waether_weekly

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midtermproject.R
import com.example.midtermproject.auth_feature.presentation.base.BaseFragment
import com.example.midtermproject.databinding.FragmentWeatherWeeklyLayoutBinding
import com.example.midtermproject.weather_feature.presentation.event.WeatherWeeklyEvent
import com.example.midtermproject.weather_feature.presentation.model.WeatherWeeklyState
import com.example.midtermproject.weather_feature.presentation.weather_weekly.WeatherWeeklyNavigationEvent
import com.example.midtermproject.weather_feature.presentation.weather_weekly.WeatherWeeklyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherWeeklyFragment : BaseFragment<FragmentWeatherWeeklyLayoutBinding>(FragmentWeatherWeeklyLayoutBinding::inflate){

    private val weatherWeeklyViewModel : WeatherWeeklyViewModel by viewModels()

    private lateinit var weatherWeeklyAdapter: WeatherWeeklyRecyclerAdapter

    override fun bind() {
        bindRecyclerView()
    }

    override fun bindViewActionListeners() {
        bindBackButton()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindWeeklyWeatherFlow()
    }

    private fun bindBackButton() {
        with(binding) {
            btnBack.setOnClickListener {
                weatherWeeklyViewModel.onEvent(WeatherWeeklyEvent.NavigateBack)
            }
        }
    }

    private fun bindRecyclerView() {
        weatherWeeklyAdapter = WeatherWeeklyRecyclerAdapter()
        binding.weatherWeeklyRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = weatherWeeklyAdapter
        }
    }

    private fun bindWeeklyWeatherFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherWeeklyViewModel.weatherWeeklyState.collect() { state ->
                    handleWeatherState(state)
                }
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherWeeklyViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is WeatherWeeklyNavigationEvent.NavigateBackToDaily -> navigateBack()
                        else -> {}
                    }
                }
            }
        }
    }

    private fun handleWeatherState(state : WeatherWeeklyState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.detailedWeatherInfo?.let { detailedWeather ->
            weatherWeeklyAdapter.submitList(detailedWeather)
        }

        state.errorMessage?.let { errorMessage ->
            showErrorScreen(errorMessage)
        }
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_weatherWeeklyFragment_to_weatherTodayFragment)
    }

    private fun showErrorScreen(message : String) {

    }
}