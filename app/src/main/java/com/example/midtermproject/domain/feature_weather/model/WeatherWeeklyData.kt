package com.example.midtermproject.domain.feature_weather.model

import java.time.LocalDateTime

data class WeatherWeeklyData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val code : Int,
)