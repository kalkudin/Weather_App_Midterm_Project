package com.example.midtermproject.presentation.weather_feature.model

data class WeatherWeeklyState(
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherWeeklyDetails>? = null,
    val errorMessage: String? = null
)
