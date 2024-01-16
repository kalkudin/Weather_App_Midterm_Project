package com.example.midtermproject.weather_feature.data.model

import com.squareup.moshi.Json

data class WeatherWeeklyDto(
    @Json(name = "hourly")
    val hourlyData : WeatherWeeklyDataDto
)