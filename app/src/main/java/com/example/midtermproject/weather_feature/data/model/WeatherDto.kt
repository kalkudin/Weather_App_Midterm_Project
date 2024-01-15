package com.example.midtermproject.weather_feature.data.model

import com.example.midtermproject.weather_feature.data.model.WeatherDataDto
import com.squareup.moshi.Json

data class WeatherDto(
    @Json(name = "hourly")
    val hourlyData : WeatherDataDto
)