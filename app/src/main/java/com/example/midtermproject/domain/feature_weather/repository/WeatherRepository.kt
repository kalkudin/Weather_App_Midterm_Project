package com.example.midtermproject.domain.feature_weather.repository

import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.domain.feature_weather.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherData(lat : Double, long : Double) : Flow<Resource<WeatherInfo>>
}