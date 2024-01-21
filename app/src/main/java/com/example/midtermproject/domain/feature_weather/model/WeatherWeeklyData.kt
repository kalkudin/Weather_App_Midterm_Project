package com.example.midtermproject.domain.weather_feature.model

import java.time.LocalDateTime

data class WeatherWeeklyData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val code : Int,
)