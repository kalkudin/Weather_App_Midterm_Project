package com.example.midtermproject.data.feature_weather.remote.model

import com.squareup.moshi.Json

data class WeatherWeeklyDto(
    @Json(name = "hourly")
    val hourlyData : WeatherWeeklyDataDto
)