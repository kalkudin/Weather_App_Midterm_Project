package com.example.midtermproject.presentation.feature_weather.mapper

import com.example.midtermproject.domain.feature_weather.model.WeatherInfo
import com.example.midtermproject.presentation.feature_weather.model.WeatherDayDetails
import com.example.midtermproject.presentation.feature_weather.util.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatTodayWeatherData(weatherInfo: WeatherInfo): List<WeatherDayDetails> {
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
            WeatherDayDetails(
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