package com.example.midtermproject.weather_feature.domain.repository

import com.example.midtermproject.auth_feature.data.remote.common.Resource
import com.example.midtermproject.weather_feature.domain.model.WeatherWeeklyInfo
import kotlinx.coroutines.flow.Flow

interface WeatherWeeklyRepository {
    suspend fun getWeatherData(lat : Double, long : Double) : Flow<Resource<WeatherWeeklyInfo>>
}