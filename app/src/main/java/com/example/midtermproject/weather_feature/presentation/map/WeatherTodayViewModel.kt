package com.example.midtermproject.weather_feature.presentation.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.auth_feature.domain.usecase.datastore_usecase.ClearSessionUseCase
import com.example.midtermproject.weather_feature.domain.usecase.GetWeatherUseCase
import com.example.midtermproject.weather_feature.presentation.map.model.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherTodayViewModel @Inject constructor(
    private val clearSessionUseCase: ClearSessionUseCase,
    private val getWeatherUseCase: GetWeatherUseCase): ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<WeatherNavigationEvent>()
    val navigationFlow : SharedFlow<WeatherNavigationEvent> = _navigationFlow.asSharedFlow()

    init {
        fetchWeatherData(lat = 52.54, long = 13.419998)
    }

    private fun fetchWeatherData(lat: Double, long: Double) {
        viewModelScope.launch {
            getWeatherUseCase(lat, long).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val weatherInfo = result.data
                        _weatherState.value = WeatherState(weatherInfo = weatherInfo)
                        weatherInfo.currentWeatherData.firstOrNull()?.let { firstWeatherData ->
                            Log.d("WeatherTodayViewModel", "First Weather Data - Time: ${firstWeatherData.time}, Temp: ${firstWeatherData.temperatureCelsius}°C")
                        }
                    }
                    is Resource.Error -> {
                        _weatherState.value = WeatherState(errorMessage = result.errorMessage)
                        Log.d("WeatherTodayViewModel", result.errorMessage)

                    }
                    is Resource.Loading -> {
                        _weatherState.value = WeatherState(isLoading = true)
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