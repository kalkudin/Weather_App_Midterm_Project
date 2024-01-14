package com.example.midtermproject.weather_feature.domain.repository

import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.weather_feature.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherData(lat : Double, long : Double) : Flow<Resource<WeatherInfo>>
}