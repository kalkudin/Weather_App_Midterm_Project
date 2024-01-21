package com.example.midtermproject.weather_feature.presentation.weather_week_day

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.weather_feature.domain.usecase.GetUserLocationUseCase
import com.example.midtermproject.weather_feature.domain.usecase.GetWeatherUseCase
import com.example.midtermproject.weather_feature.presentation.event.WeatherWeekDayEvent
import com.example.midtermproject.weather_feature.presentation.mapper.formatWeekDayWeatherData
import com.example.midtermproject.weather_feature.presentation.model.WeatherDayState
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
class WeatherWeekDayViewModel @Inject constructor(
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val getWeatherUseCase: GetWeatherUseCase
): ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherDayState())
    val weatherState: StateFlow<WeatherDayState> = _weatherState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<WeatherWeekDayNavigationEvent>()
    val navigationFlow : SharedFlow<WeatherWeekDayNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event : WeatherWeekDayEvent) {
        when(event) {
            is WeatherWeekDayEvent.NavigateToPreviousFragment -> navigateBack()
            is WeatherWeekDayEvent.LoadWeatherWithUserId -> getUserLocationAndWeather(id = event.id)
        }
    }

    private fun getUserLocationAndWeather(id : Int) {
        viewModelScope.launch {
            getUserLocationUseCase()?.let { location ->
                getWeatherData(location.latitude, location.longitude, id = id)
            } ?: run {
                _weatherState.update { WeatherDayState(errorMessage = "Location not found") }
            }
        }
    }

    private fun getWeatherData(lat: Double, long: Double, id: Int) {
        viewModelScope.launch {
            getWeatherUseCase(lat, long).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val detailedWeatherInfo = formatWeekDayWeatherData(result.data, id = id)
                        Log.d("WeatherVM", "Formatted weather data: $detailedWeatherInfo")
                        _weatherState.update { WeatherDayState(detailedWeatherInfo = detailedWeatherInfo) }
                    }
                    is Resource.Error -> {
                        _weatherState.update { WeatherDayState(errorMessage = result.errorMessage) }
                    }
                    is Resource.Loading -> {
                        _weatherState.update { WeatherDayState(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _navigationFlow.emit(WeatherWeekDayNavigationEvent.NavigateToPreviousFragment)
        }
    }
}

sealed class WeatherWeekDayNavigationEvent {
    data object NavigateToPreviousFragment : WeatherWeekDayNavigationEvent()
}