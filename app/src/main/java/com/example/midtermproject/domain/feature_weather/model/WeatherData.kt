package com.example.midtermproject.domain.weather_feature.model

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val code : Int,
    val windSpeed : Double,
    val relativeHumidity : Int
)
