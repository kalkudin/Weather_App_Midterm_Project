package com.example.midtermproject.weather_feature.presentation.model

data class WeatherWeeklyState(
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherWeeklyDetails>? = null,
    val errorMessage: String? = null
)
