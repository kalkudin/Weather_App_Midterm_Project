package com.example.midtermproject.presentation.weather_feature.model

import com.example.midtermproject.presentation.weather_feature.util.WeatherType

data class WeatherCityDetails(
    val lat : Double,
    val long : Double,
    val id: Int,
    val formattedDate: String,
    val temperatureCelsius: Double,
    val weatherType: WeatherType,
    val iconRes: Int,
)