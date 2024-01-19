package com.example.midtermproject.weather_feature.presentation.model

import com.example.midtermproject.weather_feature.presentation.util.WeatherType

data class WeatherTodayDetails(
    val formattedDate: String,
    val formattedTime: String,
    val temperatureCelsius: Double,
    val weatherType: WeatherType,
    val windSpeed: Double,
    val relativeHumidity: Int,
    val iconRes: Int,
    val weatherDescription: String
)