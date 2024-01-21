package com.example.midtermproject.domain.weather_feature.repository

import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.domain.weather_feature.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherData(lat : Double, long : Double) : Flow<Resource<WeatherInfo>>
}