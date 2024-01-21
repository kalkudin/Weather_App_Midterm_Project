package com.example.midtermproject.weather_feature.presentation.model

data class WeatherCityState (
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherCityDetails>? = null,
    val errorMessage: String? = null
)
