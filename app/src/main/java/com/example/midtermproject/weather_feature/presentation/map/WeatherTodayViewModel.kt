package com.example.midtermproject.weather_feature.presentation.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.auth_feature.domain.usecase.datastore_usecase.ClearSessionUseCase
import com.example.midtermproject.weather_feature.domain.usecase.GetUserLocationUseCase
import com.example.midtermproject.weather_feature.domain.usecase.GetWeatherUseCase
import com.example.midtermproject.weather_feature.presentation.mapper.extractRelevantWeatherData
import com.example.midtermproject.weather_feature.presentation.model.WeatherState
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
class WeatherTodayViewModel @Inject constructor(
    private val clearSessionUseCase: ClearSessionUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase
): ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<WeatherNavigationEvent>()
    val navigationFlow : SharedFlow<WeatherNavigationEvent> = _navigationFlow.asSharedFlow()

    init {
        fetchUserLocationAndWeather()
    }

    private fun fetchUserLocationAndWeather() {
        viewModelScope.launch {
            val location = getUserLocationUseCase()
            if (location != null) {
                fetchWeatherData(location.latitude, location.longitude)
            } else {
                _weatherState.update { WeatherState(errorMessage = "Location not found") }
            }
        }
    }

    private fun fetchWeatherData(lat: Double, long: Double) {
        viewModelScope.launch {
            getWeatherUseCase(lat, long).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val weatherInfo = result.data
                        val detailedWeatherInfo = extractRelevantWeatherData(weatherInfo)

                        Log.d("WeatherTodayViewModel", "Fetched detailed weather data: $detailedWeatherInfo")

                        _weatherState.update { WeatherState(detailedWeatherInfo = detailedWeatherInfo) }
                    }
                    is Resource.Error -> {
                        Log.e("WeatherTodayViewModel", "Error fetching weather data: ${result.errorMessage}")
                        _weatherState.update { WeatherState(errorMessage = result.errorMessage) }
                    }
                    is Resource.Loading -> {
                        Log.d("WeatherTodayViewModel", "Loading weather data")
                        _weatherState.update { WeatherState(isLoading = true) }
                    }
                }
            }
        }
    }

    fun onEvent(event : WeatherTodayEvent){
        when(event) {
            is WeatherTodayEvent.LogOut -> logout()
        }
    }

    private fun logout(){
        viewModelScope.launch {
            clearSessionUseCase()
            _navigationFlow.emit(WeatherNavigationEvent.NavigateToHome)
        }
    }
}

sealed class WeatherNavigationEvent {
    data object NavigateToHome : WeatherNavigationEvent()
}