package com.example.midtermproject.weather_feature.presentation.model

import java.time.LocalDateTime

data class WeatherDetailedInfo(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val weatherType: WeatherType,
    val windSpeed: Double,
    val relativeHumidity: Int,
    val iconRes: Int,
    val weatherDescription: String
)