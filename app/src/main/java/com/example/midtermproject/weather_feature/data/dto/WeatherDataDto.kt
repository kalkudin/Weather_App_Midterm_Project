package com.example.midtermproject.weather_feature.data.dto

import com.squareup.moshi.Json

data class WeatherDataDto(
    @Json(name = "time")
    val times : List<String>,
    @Json(name = "temperature_2m")
    val temperatures : List<Double>
)
