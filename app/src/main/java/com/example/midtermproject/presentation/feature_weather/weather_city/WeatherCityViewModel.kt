package com.example.midtermproject.presentation.feature_weather.weather_city

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.domain.feature_weather.usecase.GetCoordinatesUseCase
import com.example.midtermproject.domain.feature_weather.usecase.GetWeeklyWeatherUseCase
import com.example.midtermproject.presentation.feature_weather.event.WeatherCityEvent
import com.example.midtermproject.presentation.feature_weather.mapper.formatCityWeatherData
import com.example.midtermproject.presentation.feature_weather.model.WeatherCityState
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
class WeatherCityViewModel @Inject constructor(
    private val getCoordinatesUseCase: GetCoordinatesUseCase,
    private val getWeeklyWeatherUseCase: GetWeeklyWeatherUseCase
) : ViewModel() {

    private val _weatherCityState = MutableStateFlow(WeatherCityState())
    val weatherCityState : StateFlow<WeatherCityState> = _weatherCityState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<WeatherCityNavigationEvent>()
    val navigationFlow: SharedFlow<WeatherCityNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event: WeatherCityEvent) {
        when (event) {
            is WeatherCityEvent.GetWeatherForCity -> getCityCoordinates(city = event.city)
            is WeatherCityEvent.NavigateBack -> navigateBack()
        }
    }

    private fun getCityCoordinates(city: String) {
        viewModelScope.launch {
            getCoordinatesUseCase(cityName = city).collect { result ->
                Log.d("GeocodingResult", "Result for $city: $result")
                result.onSuccess { latLng ->
                    getWeatherForCity(lat = latLng.latitude, long = latLng.longitude)
                }
            }
        }
    }

    private fun getWeatherForCity(lat : Double, long : Double) {
        viewModelScope.launch {
            getWeeklyWeatherUseCase(lat = lat, long = long).collect() { result ->
                when(result) {
                    is Resource.Success -> {
                        val cityWeatherInfo = formatCityWeatherData(result.data.weeklyWeatherData, lat = lat, long = long)
                        _weatherCityState.update { WeatherCityState(detailedWeatherInfo = cityWeatherInfo) }
                    }
                    is Resource.Error -> {
                        _weatherCityState.update { WeatherCityState(errorMessage = result.errorMessage) }
                    }
                    is Resource.Loading -> {
                        _weatherCityState.update { WeatherCityState(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _navigationFlow.emit(WeatherCityNavigationEvent.NavigateBack)
        }
    }
}

sealed class WeatherCityNavigationEvent {
    data object NavigateBack : WeatherCityNavigationEvent()
    data class NavigateToItemClicked(val id: Int) : WeatherCityNavigationEvent()
}