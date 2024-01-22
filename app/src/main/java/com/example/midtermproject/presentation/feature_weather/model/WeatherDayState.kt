package com.example.midtermproject.presentation.feature_weather.model

data class WeatherDayState(
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherDayDetails>? = null,
    val errorMessage: String? = null
)