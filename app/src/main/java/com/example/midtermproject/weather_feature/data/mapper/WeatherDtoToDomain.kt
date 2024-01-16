package com.example.midtermproject.weather_feature.data.mapper

import com.example.midtermproject.weather_feature.data.model.WeatherDto
import com.example.midtermproject.weather_feature.domain.model.WeatherData
import com.example.midtermproject.weather_feature.domain.model.WeatherInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun WeatherDto.toDomain(): WeatherInfo {
    val weatherDataList = this.hourlyData.times
        .zip(this.hourlyData.temperatures) { time: String, temperature: Double ->
            Pair(time, temperature)
        }
        .zip(this.hourlyData.weatherCode) { timeTempPair: Pair<String, Double>, weatherCode: Int ->
            Triple(timeTempPair.first, timeTempPair.second, weatherCode)
        }
        .zip(this.hourlyData.windSpeed) { timeTempCodeTriple: Triple<String, Double, Int>,
                                          windSpeed: Double ->
            val (time, temperature, weatherCode) = timeTempCodeTriple
            val relativeHumidity = this.hourlyData.relativeHumidity[this.hourlyData.times.indexOf(time)] // Get the corresponding relative humidity
            WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                code = weatherCode,
                windSpeed = windSpeed,
                relativeHumidity = relativeHumidity
            )
        }
    return WeatherInfo(weatherDataList)
}