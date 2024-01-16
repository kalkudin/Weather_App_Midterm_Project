package com.example.midtermproject.weather_feature.presentation.model

data class WeatherTodayState(
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherTodayDetails>? = null,
    val errorMessage: String? = null
)