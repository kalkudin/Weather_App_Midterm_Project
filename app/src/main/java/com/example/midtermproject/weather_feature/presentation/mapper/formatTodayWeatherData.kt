package com.example.midtermproject.weather_feature.presentation.mapper

import com.example.midtermproject.weather_feature.domain.model.WeatherInfo
import com.example.midtermproject.weather_feature.presentation.model.WeatherTodayDetails
import com.example.midtermproject.weather_feature.presentation.util.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatTodayWeatherData(weatherInfo: WeatherInfo): List<WeatherTodayDetails> {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val dateOutputFormatter = DateTimeFormatter.ofPattern("MMMM dd", Locale.US)
    val timeOutputFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val relevantIndices = listOf(15)
    return weatherInfo.currentWeatherData
        .filterIndexed { index, _ -> index in relevantIndices }
        .map { weatherData ->
            val dateTime = LocalDateTime.parse(weatherData.time.toString(), inputFormatter)
            val formattedDate = dateTime.format(dateOutputFormatter)
            val formattedTime = dateTime.format(timeOutputFormatter)

            val weatherType = WeatherType.fromWMO(weatherData.code)
            WeatherTodayDetails(
                formattedDate = formattedDate,
                formattedTime = formattedTime,
                temperatureCelsius = weatherData.temperatureCelsius,
                weatherType = weatherType,
                windSpeed = weatherData.windSpeed,
                relativeHumidity = weatherData.relativeHumidity,
                iconRes = weatherType.iconRes,
                weatherDescription = weatherType.weatherDesc
            )
        }
}