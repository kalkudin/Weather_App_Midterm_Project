package com.example.midtermproject.weather_feature.domain.model

import java.time.LocalDateTime

data class WeatherWeeklyData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val code : Int,
)