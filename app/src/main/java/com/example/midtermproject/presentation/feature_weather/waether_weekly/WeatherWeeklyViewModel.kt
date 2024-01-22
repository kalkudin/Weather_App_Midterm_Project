package com.example.midtermproject.presentation.feature_weather.waether_weekly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.domain.feature_weather.usecase.GetUserLocationUseCase
import com.example.midtermproject.domain.feature_weather.usecase.GetWeeklyWeatherUseCase
import com.example.midtermproject.presentation.feature_weather.event.WeatherWeeklyEvent
import com.example.midtermproject.presentation.feature_weather.mapper.formatWeeklyWeatherData
import com.example.midtermproject.presentation.feature_weather.model.WeatherWeeklyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherWeeklyViewModel @Inject constructor(
    private val getWeeklyWeatherUseCase: GetWeeklyWeatherUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase
): ViewModel() {

    private val _weatherWeeklyState = MutableStateFlow(WeatherWeeklyState())
    val weatherWeeklyState: StateFlow<WeatherWeeklyState> = _weatherWeeklyState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<WeatherWeeklyNavigationEvent>()
    val navigationFlow: SharedFlow<WeatherWeeklyNavigationEvent> = _navigationFlow.asSharedFlow()

    init {
        fetchUserLocationAndWeather()
    }

    fun onEvent(event : WeatherWeeklyEvent) {
        when(event) {
            is WeatherWeeklyEvent.NavigateToPreviousFragment -> navigateBack()
            is WeatherWeeklyEvent.ItemClicked -> navigateToWeekDayFragment(event.id)
        }
    }

    private fun fetchUserLocationAndWeather() {
        viewModelScope.launch {
            getUserLocationUseCase()?.let { location ->
                fetchWeeklyWeatherData(location.latitude, location.longitude)
            } ?: run {
                _weatherWeeklyState.update { WeatherWeeklyState(errorMessage = "Location not found") }
            }
        }
    }

    private fun fetchWeeklyWeatherData(lat: Double, long: Double) {
        viewModelScope.launch {
            getWeeklyWeatherUseCase(lat, long).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val detailedWeatherInfo = formatWeeklyWeatherData(result.data.weeklyWeatherData)
                        _weatherWeeklyState.update { WeatherWeeklyState(detailedWeatherInfo = detailedWeatherInfo) }
                    }

                    is Resource.Error -> {
                        _weatherWeeklyState.update { WeatherWeeklyState(errorMessage = result.errorMessage) }
                    }

                    is Resource.Loading -> {
                        _weatherWeeklyState.update { WeatherWeeklyState(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _navigationFlow.emit(WeatherWeeklyNavigationEvent.NavigateBackToDaily)
        }
    }

    private fun navigateToWeekDayFragment(id : Int) {
        viewModelScope.launch {
            _navigationFlow.emit(WeatherWeeklyNavigationEvent.NavigateToWeekDayFragment(id = id))
        }
    }
}

sealed class WeatherWeeklyNavigationEvent(){
    data object NavigateBackToDaily : WeatherWeeklyNavigationEvent()
    data class NavigateToWeekDayFragment(val id : Int) : WeatherWeeklyNavigationEvent()
}