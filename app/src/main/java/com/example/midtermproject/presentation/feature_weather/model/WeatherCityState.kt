package com.example.midtermproject.presentation.feature_weather.model

data class WeatherCityState (
    val isLoading: Boolean = false,
    val detailedWeatherInfo: List<WeatherCityDetails>? = null,
    val errorMessage: String? = null
)
