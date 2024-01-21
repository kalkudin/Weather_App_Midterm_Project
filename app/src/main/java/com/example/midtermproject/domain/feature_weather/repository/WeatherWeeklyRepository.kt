package com.example.midtermproject.domain.weather_feature.repository

import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.domain.weather_feature.model.WeatherWeeklyInfo
import kotlinx.coroutines.flow.Flow

interface WeatherWeeklyRepository {
    suspend fun getWeatherData(lat : Double, long : Double) : Flow<Resource<WeatherWeeklyInfo>>
}