package com.example.midtermproject.weather_feature.data.remote.model

import com.squareup.moshi.Json

data class WeatherWeeklyDto(
    @Json(name = "hourly")
    val hourlyData : WeatherWeeklyDataDto
)