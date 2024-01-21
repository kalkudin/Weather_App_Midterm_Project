package com.example.midtermproject.data.feature_weather.remote.model

import com.squareup.moshi.Json

data class WeatherDataDto(
    @Json(name = "time")
    val times : List<String>,
    @Json(name = "temperature_2m")
    val temperatures : List<Double>,
    @Json(name = "weathercode")
    val weatherCode : List<Int>,
    @Json(name = "windspeed_10m")
    val windSpeed : List<Double>,
    @Json(name = "relative_humidity_2m")
    val relativeHumidity : List<Int>
)
