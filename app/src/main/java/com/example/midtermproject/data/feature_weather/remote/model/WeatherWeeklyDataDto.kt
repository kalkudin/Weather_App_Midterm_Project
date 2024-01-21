package com.example.midtermproject.data.feature_weather.remote.model

import com.squareup.moshi.Json

data class WeatherWeeklyDataDto(
    @Json(name = "time")
    val times : List<String>,
    @Json(name = "temperature_2m")
    val temperatures : List<Double>,
    @Json(name = "weathercode")
    val weatherCode : List<Int>,
)