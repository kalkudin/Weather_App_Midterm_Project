package com.example.midtermproject.presentation.weather_feature.mapper

import com.example.midtermproject.domain.weather_feature.model.WeatherInfo
import com.example.midtermproject.presentation.weather_feature.model.WeatherDayDetails
import com.example.midtermproject.presentation.weather_feature.util.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatWeekDayWeatherData(weatherInfo: WeatherInfo, id : Int): List<WeatherDayDetails> {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val dateOutputFormatter = DateTimeFormatter.ofPattern("MMMM dd", Locale.US)
    val timeOutputFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val relevantIndices = listOf(id + 8 , id + 5, id + 2, id, id - 2, id - 5, id - 8)
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