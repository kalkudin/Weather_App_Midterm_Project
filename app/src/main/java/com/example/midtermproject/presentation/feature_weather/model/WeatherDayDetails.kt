package com.example.midtermproject.presentation.feature_weather.model

import com.example.midtermproject.presentation.feature_weather.util.WeatherType

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