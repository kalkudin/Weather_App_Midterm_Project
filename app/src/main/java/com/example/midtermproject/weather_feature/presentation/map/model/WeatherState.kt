package com.example.midtermproject.weather_feature.presentation.map.model

import com.example.midtermproject.weather_feature.domain.model.WeatherInfo

data class WeatherState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val errorMessage: String? = null
)