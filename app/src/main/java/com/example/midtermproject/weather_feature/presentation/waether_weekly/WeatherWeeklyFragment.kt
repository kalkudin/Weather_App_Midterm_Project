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
import com.example.midtermproject.weather_feature.presentation.model.WeatherWeeklyDetails
import com.example.midtermproject.weather_feature.presentation.model.WeatherWeeklyState
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
                weatherWeeklyViewModel.onEvent(WeatherWeeklyEvent.NavigateToPreviousFragment)
            }
        }
    }

    private fun bindRecyclerView() {
        weatherWeeklyAdapter = WeatherWeeklyRecyclerAdapter(itemClickListener = { weatherWeeklyDetails ->
            handleItemClick(weatherWeeklyDetails)
        })
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
                        is WeatherWeeklyNavigationEvent.NavigateToWeekDayFragment -> navigateToFragmentWithAction(event.id)
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

    private fun handleItemClick(weatherWeeklyDetails: WeatherWeeklyDetails) {
        weatherWeeklyViewModel.onEvent(WeatherWeeklyEvent.ItemClicked(id = weatherWeeklyDetails.id))
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_weatherWeeklyFragment_to_weatherTodayFragment)
    }

    private fun navigateToFragmentWithAction(id : Int) {
        val action =WeatherWeeklyFragmentDirections.actionWeatherWeeklyFragmentToWeatherWeekDayFragment(id)
        findNavController().navigate(action)
    }

    private fun showErrorScreen(message : String) {

    }
}