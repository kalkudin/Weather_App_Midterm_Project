package com.example.midtermproject.weather_feature.data.model

import com.squareup.moshi.Json

data class WeatherDataDto(
    @Json(name = "time")
    val times : List<String>,
    @Json(name = "temperature_2m")
    val temperatures : List<Double>,
    @Json(name = "weathercode")
    val weatherCode : List<Int>,
    @Json(name = "windspeed_10m")
    val windSpeed : List<Double>
)
