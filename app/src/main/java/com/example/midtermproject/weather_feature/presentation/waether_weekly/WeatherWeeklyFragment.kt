package com.example.midtermproject.weather_feature.presentation.waether_weekly

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.midtermproject.R
import com.example.midtermproject.auth_feature.presentation.base.BaseFragment
import com.example.midtermproject.databinding.FragmentWeatherWeeklyLayoutBinding
import com.example.midtermproject.weather_feature.presentation.event.WeatherWeeklyEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherWeeklyFragment : BaseFragment<FragmentWeatherWeeklyLayoutBinding>(FragmentWeatherWeeklyLayoutBinding::inflate){

    private val weatherWeeklyViewModel : WeatherWeeklyViewModel by viewModels()

    override fun bind() {

    }

    override fun bindViewActionListeners() {
        bindBackButton()
    }

    override fun bindObservers() {
        bindNavigationFlow()
    }

    private fun bindBackButton() {
        with(binding) {
            btnBack.setOnClickListener {
                weatherWeeklyViewModel.onEvent(WeatherWeeklyEvent.NavigateBack)
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherWeeklyViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is WeatherWeeklyNavigationEvent.NavigateBackToDaily -> navigateBack()
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_weatherWeeklyFragment_to_weatherTodayFragment)
    }
}