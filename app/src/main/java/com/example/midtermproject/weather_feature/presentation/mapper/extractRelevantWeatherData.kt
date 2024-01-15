package com.example.midtermproject.weather_feature.presentation.mapper

import com.example.midtermproject.weather_feature.domain.model.WeatherInfo
import com.example.midtermproject.weather_feature.presentation.model.WeatherDetailedInfo
import com.example.midtermproject.weather_feature.presentation.model.WeatherType

fun extractRelevantWeatherData(weatherInfo: WeatherInfo): List<WeatherDetailedInfo> {
    val relevantIndices = listOf(0, 5, 11, 17, 23)
    return weatherInfo.currentWeatherData
        .filterIndexed { index, _ -> index in relevantIndices }
        .map { weatherData ->
            val weatherType = WeatherType.fromWMO(weatherData.code)
            WeatherDetailedInfo(
                time = weatherData.time,
                temperatureCelsius = weatherData.temperatureCelsius,
                weatherType = weatherType,
                windSpeed = weatherData.windSpeed,
                relativeHumidity = weatherData.relativeHumidity,
                iconRes = weatherType.iconRes, // Set icon resource
                weatherDescription = weatherType.weatherDesc // Set weather description
            )
        }
}