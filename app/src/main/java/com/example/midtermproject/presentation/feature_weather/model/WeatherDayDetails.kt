package com.example.midtermproject.presentation.weather_feature.model

import com.example.midtermproject.presentation.weather_feature.util.WeatherType

data class WeatherDayDetails(
    val formattedDate: String,
    val formattedTime: String,
    val temperatureCelsius: Double,
    val weatherType: WeatherType,
    val windSpeed: Double,
    val relativeHumidity: Int,
    val iconRes: Int,
    val weatherDescription: String
)