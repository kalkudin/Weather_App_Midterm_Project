package com.example.midtermproject.weather_feature.presentation.mapper

import com.example.midtermproject.weather_feature.domain.model.WeatherWeeklyData
import com.example.midtermproject.weather_feature.presentation.model.WeatherType
import com.example.midtermproject.weather_feature.presentation.model.WeatherWeeklyDetails
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatWeeklyWeatherData(weeklyData: List<WeatherWeeklyData>): List<WeatherWeeklyDetails> {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val dateOutputFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd", Locale.US)

    val relevantIndices = listOf(15, 39, 63, 87, 111, 135, 159)

    return weeklyData.filterIndexed { index, _ -> index in relevantIndices }
        .mapIndexed { index, weatherData ->
            val dateTime = LocalDateTime.parse(weatherData.time.toString(), inputFormatter)
            val formattedDate = dateTime.format(dateOutputFormatter)

            val weatherType = WeatherType.fromWMO(weatherData.code)
            WeatherWeeklyDetails(
                id = relevantIndices[index],
                formattedDate = formattedDate,
                temperatureCelsius = weatherData.temperatureCelsius,
                weatherType = weatherType,
                iconRes = weatherType.iconRes
            )
        }
}