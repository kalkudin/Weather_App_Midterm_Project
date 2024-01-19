package com.example.midtermproject.weather_feature.presentation.model

data class WeatherDayState(
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherDayDetails>? = null,
    val errorMessage: String? = null
)