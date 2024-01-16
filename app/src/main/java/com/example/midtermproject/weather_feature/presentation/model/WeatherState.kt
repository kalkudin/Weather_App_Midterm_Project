package com.example.midtermproject.weather_feature.presentation.model

data class WeatherState(
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherTodayInfo>? = null,
    val errorMessage: String? = null
)