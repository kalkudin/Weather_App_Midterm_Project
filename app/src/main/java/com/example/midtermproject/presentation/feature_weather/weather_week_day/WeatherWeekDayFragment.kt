package com.example.midtermproject.presentation.weather_feature.weather_week_day

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midtermproject.R
import com.example.midtermproject.databinding.FragmentWeatherWeekDayLayoutBinding
import com.example.midtermproject.presentation.base.BaseFragment
import com.example.midtermproject.presentation.weather_feature.event.WeatherWeekDayEvent
import com.example.midtermproject.presentation.weather_feature.model.WeatherDayState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherWeekDayFragment : BaseFragment<FragmentWeatherWeekDayLayoutBinding>(FragmentWeatherWeekDayLayoutBinding::inflate) {

    private val weatherWeekDayViewModel : WeatherWeekDayViewModel by viewModels()

    private val weatherWeekDayRecyclerAdapter = WeatherWeekDayRecyclerAdapter()

    override fun bind() {
        bindRecyclerView()
        bindWeatherData()
    }
    override fun bindViewActionListeners() {
        bindBackBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindWeatherFlow()
    }

    private fun bindWeatherData() {
        val id = arguments?.getInt("id")
        weatherWeekDayViewModel.onEvent(WeatherWeekDayEvent.LoadWeatherWithUserId(id = id!!))
    }

    private fun bindRecyclerView() {
        binding.weatherWeekDayRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = weatherWeekDayRecyclerAdapter
        }
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                weatherWeekDayViewModel.onEvent(WeatherWeekDayEvent.NavigateToPreviousFragment)
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherWeekDayViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is WeatherWeekDayNavigationEvent.NavigateToPreviousFragment -> navigateToPreviousFragment()
                        else -> {}
                    }
                }
            }
        }
    }

    private fun bindWeatherFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherWeekDayViewModel.weatherState.collect { state ->
                    handleWeatherState(state)
                }
            }
        }
    }

    private fun handleWeatherState(state : WeatherDayState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.detailedWeatherInfo?.let { detailedWeather ->
            weatherWeekDayRecyclerAdapter.submitList(detailedWeather)
        }

        state.errorMessage?.let { errorMessage ->
            showErrorScreen(errorMessage)
        }
    }

    private fun navigateToPreviousFragment() {
        findNavController().navigate(R.id.action_weatherWeekDayFragment_to_weatherWeeklyFragment)
    }

    private fun showErrorScreen(errorMessage : String) {

    }
}