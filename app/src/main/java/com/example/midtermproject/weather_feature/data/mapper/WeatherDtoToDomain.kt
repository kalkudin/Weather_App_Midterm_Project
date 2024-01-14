package com.example.midtermproject.weather_feature.data.mapper

import com.example.midtermproject.weather_feature.data.dto.WeatherDto
import com.example.midtermproject.weather_feature.domain.model.WeatherData
import com.example.midtermproject.weather_feature.domain.model.WeatherInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun WeatherDto.toDomain(): WeatherInfo {
    val weatherDataList = this.hourlyData.times.zip(this.hourlyData.temperatures).map { (time, temperature) ->
        WeatherData(
            time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
            temperatureCelsius = temperature
        )
    }
    return WeatherInfo(weatherDataList)
}