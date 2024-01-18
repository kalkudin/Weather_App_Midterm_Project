package com.example.midtermproject.weather_feature.data.remote.model

import com.squareup.moshi.Json

data class WeatherDto(
    @Json(name = "hourly")
    val hourlyData : WeatherDataDto
)