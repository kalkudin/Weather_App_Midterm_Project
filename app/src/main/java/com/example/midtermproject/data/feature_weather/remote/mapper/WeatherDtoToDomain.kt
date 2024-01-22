package com.example.midtermproject.data.feature_weather.remote.mapper

import com.example.midtermproject.data.feature_weather.remote.model.WeatherDto
import com.example.midtermproject.domain.feature_weather.model.WeatherData
import com.example.midtermproject.domain.feature_weather.model.WeatherInfo
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
            val relativeHumidity = this.hourlyData.relativeHumidity[this.hourlyData.times.indexOf(time)]
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