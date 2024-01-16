package com.example.midtermproject.weather_feature.presentation.weather_weekly

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.auth_feature.data.remote.common.Resource
import com.example.midtermproject.weather_feature.domain.usecase.GetUserLocationUseCase
import com.example.midtermproject.weather_feature.domain.usecase.GetWeeklyWeatherUseCase
import com.example.midtermproject.weather_feature.presentation.event.WeatherWeeklyEvent
import com.example.midtermproject.weather_feature.presentation.mapper.formatWeeklyWeatherData
import com.example.midtermproject.weather_feature.presentation.model.WeatherWeeklyState
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

    private fun fetchUserLocationAndWeather() {
        viewModelScope.launch {
            val location = getUserLocationUseCase()
            if (location != null) {
                fetchWeeklyWeatherData(location.latitude, location.longitude)
            } else {
                _weatherWeeklyState.update { WeatherWeeklyState(errorMessage = "Location not found") }
            }
        }
    }

    private fun fetchWeeklyWeatherData(lat: Double, long: Double) {
        viewModelScope.launch {
            getWeeklyWeatherUseCase(lat, long).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("WeatherWeeklyVM", "Weather data fetched successfully")
                        val detailedWeatherInfo = formatWeeklyWeatherData(result.data.weeklyWeatherData)
                        Log.d("WeatherWeeklyVM", "Formatted Weekly Weather Data: $detailedWeatherInfo")
                        _weatherWeeklyState.update {
                            WeatherWeeklyState(isLoading = false, detailedWeatherInfo = detailedWeatherInfo)
                        }
                    }
                    is Resource.Error -> {
                        _weatherWeeklyState.update {
                            WeatherWeeklyState(isLoading = false, errorMessage = result.errorMessage)
                        }
                    }
                    is Resource.Loading -> {
                        _weatherWeeklyState.update {
                            WeatherWeeklyState(isLoading = true)
                        }
                    }
                }
            }
        }
    }

     fun onEvent(event : WeatherWeeklyEvent) {
         when(event) {
             is WeatherWeeklyEvent.NavigateBack -> navigateBack()
         }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _navigationFlow.emit(WeatherWeeklyNavigationEvent.NavigateBackToDaily)
        }
    }
}

sealed class WeatherWeeklyNavigationEvent(){
    data object NavigateBackToDaily : WeatherWeeklyNavigationEvent()
}