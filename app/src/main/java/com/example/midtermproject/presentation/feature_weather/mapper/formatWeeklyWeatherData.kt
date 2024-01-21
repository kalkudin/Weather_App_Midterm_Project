package com.example.midtermproject.presentation.weather_feature.mapper

import com.example.midtermproject.domain.weather_feature.model.WeatherWeeklyData
import com.example.midtermproject.presentation.weather_feature.util.WeatherType
import com.example.midtermproject.presentation.weather_feature.model.WeatherWeeklyDetails
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