package com.example.midtermproject.presentation.feature_weather.mapper

import com.example.midtermproject.domain.feature_weather.model.WeatherWeeklyData
import com.example.midtermproject.presentation.feature_weather.model.WeatherCityDetails
import com.example.midtermproject.presentation.feature_weather.util.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatCityWeatherData(weeklyData: List<WeatherWeeklyData>, lat : Double, long : Double): List<WeatherCityDetails> {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val dateOutputFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd", Locale.US)

    val relevantIndices = listOf(15, 39, 63, 87, 111, 135, 159)

    return weeklyData.filterIndexed { index, _ -> index in relevantIndices }
        .mapIndexed { index, weatherData ->
            val dateTime = LocalDateTime.parse(weatherData.time.toString(), inputFormatter)
            val formattedDate = dateTime.format(dateOutputFormatter)

            val weatherType = WeatherType.fromWMO(weatherData.code)
            WeatherCityDetails(
                lat = lat,
                long = long,
                id = relevantIndices[index],
                formattedDate = formattedDate,
                temperatureCelsius = weatherData.temperatureCelsius,
                weatherType = weatherType,
                iconRes = weatherType.iconRes
            )
        }
}