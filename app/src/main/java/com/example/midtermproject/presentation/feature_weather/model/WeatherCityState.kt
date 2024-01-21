package com.example.midtermproject.presentation.weather_feature.model

data class WeatherCityState (
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherCityDetails>? = null,
    val errorMessage: String? = null
)
