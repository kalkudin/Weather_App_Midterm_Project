package com.example.midtermproject.presentation.feature_weather.model

data class WeatherWeeklyState(
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherWeeklyDetails>? = null,
    val errorMessage: String? = null
)
