package com.example.midtermproject.weather_feature.domain.model

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val code : Int,
    val windSpeed : Double
)
