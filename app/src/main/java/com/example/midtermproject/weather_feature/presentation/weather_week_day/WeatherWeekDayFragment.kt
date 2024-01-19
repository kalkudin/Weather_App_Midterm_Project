package com.example.midtermproject.weather_feature.presentation.weather_week_day

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.midtermproject.R
import com.example.midtermproject.auth_feature.presentation.base.BaseFragment
import com.example.midtermproject.databinding.FragmentWeatherWeekDayLayoutBinding
import com.example.midtermproject.weather_feature.presentation.event.WeatherWeekDayEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherWeekDayFragment : BaseFragment<FragmentWeatherWeekDayLayoutBinding>(FragmentWeatherWeekDayLayoutBinding::inflate) {

    private val weatherWeekDayViewModel : WeatherWeekDayViewModel by viewModels()

    override fun bind() {
        val userId = arguments?.getInt("userId")
    }
    override fun bindViewActionListeners() {
        bindBackBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
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
                    }
                }
            }
        }
    }

    private fun navigateToPreviousFragment() {
        findNavController().navigate(R.id.action_weatherWeekDayFragment_to_weatherWeeklyFragment)
    }
}