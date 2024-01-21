package com.example.midtermproject.data.feature_weather.remote.mapper

import com.example.midtermproject.data.feature_weather.remote.model.WeatherWeeklyDto
import com.example.midtermproject.domain.weather_feature.model.WeatherWeeklyData
import com.example.midtermproject.domain.weather_feature.model.WeatherWeeklyInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun WeatherWeeklyDto.toDomain(): WeatherWeeklyInfo {
    val weatherDataList = this.hourlyData.times
        .zip(this.hourlyData.temperatures) { time, temperature ->
            Pair(time, temperature)
        }
        .zip(this.hourlyData.weatherCode) { timeTempPair, weatherCode ->
            Triple(timeTempPair.first, timeTempPair.second, weatherCode)
        }
        .map { (time, temperature, weatherCode) ->
            WeatherWeeklyData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                code = weatherCode
            )
        }
    return WeatherWeeklyInfo(weeklyWeatherData = weatherDataList)
}