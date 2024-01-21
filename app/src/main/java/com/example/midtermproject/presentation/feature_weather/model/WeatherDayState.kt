package com.example.midtermproject.presentation.weather_feature.model

data class WeatherDayState(
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherDayDetails>? = null,
    val errorMessage: String? = null
)