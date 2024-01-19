package com.example.midtermproject.weather_feature.presentation.weather_today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.auth_feature.domain.usecase.datastore_usecase.ClearSessionUseCase
import com.example.midtermproject.weather_feature.domain.usecase.GetUserLocationUseCase
import com.example.midtermproject.weather_feature.domain.usecase.GetWeatherUseCase
import com.example.midtermproject.weather_feature.presentation.event.WeatherTodayEvent
import com.example.midtermproject.weather_feature.presentation.mapper.formatTodayWeatherData
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
class WeatherTodayViewModel @Inject constructor(
    private val clearSessionUseCase: ClearSessionUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase
): ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherDayState())
    val weatherState: StateFlow<WeatherDayState> = _weatherState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<WeatherNavigationEvent>()
    val navigationFlow : SharedFlow<WeatherNavigationEvent> = _navigationFlow.asSharedFlow()

    init {
        fetchUserLocationAndWeather()
    }

    fun onEvent(event : WeatherTodayEvent){
        when(event) {
            is WeatherTodayEvent.LogOut -> logout()
            is WeatherTodayEvent.MoveToDetailsPage -> moveToWeeklyPage()
            is WeatherTodayEvent.RefreshPage -> getFreshData()
        }
    }

    private fun fetchUserLocationAndWeather() {
        viewModelScope.launch {
            getUserLocationUseCase()?.let { location ->
                fetchWeatherData(location.latitude, location.longitude)
            } ?: run {
                _weatherState.update { WeatherDayState(errorMessage = "Location not found") }
            }
        }
    }

    private fun fetchWeatherData(lat: Double, long: Double) {
        viewModelScope.launch {
            getWeatherUseCase(lat, long).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val detailedWeatherInfo = formatTodayWeatherData(result.data)
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

    private fun logout() {
        viewModelScope.launch {
            clearSessionUseCase()
            _navigationFlow.emit(WeatherNavigationEvent.NavigateToHome)
        }
    }

    private fun moveToWeeklyPage() {
        viewModelScope.launch {
            _navigationFlow.emit(WeatherNavigationEvent.NavigateToWeeklyWeather)
        }
    }

    private fun getFreshData(){
        fetchUserLocationAndWeather()
    }
}

sealed class WeatherNavigationEvent {
    data object NavigateToHome : WeatherNavigationEvent()
    data object NavigateToWeeklyWeather : WeatherNavigationEvent()
}