package com.example.midtermproject.weather_feature.presentation.model

import com.example.midtermproject.weather_feature.presentation.util.WeatherType

data class WeatherWeeklyDetails(
    val id: Int,
    val formattedDate: String,
    val temperatureCelsius: Double,
    val weatherType: WeatherType,
    val iconRes: Int,
)