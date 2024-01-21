package com.example.midtermproject.data.feature_weather.remote.repository

import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.data.common.HandleResponse
import com.example.midtermproject.data.feature_weather.remote.mapper.mapResource
import com.example.midtermproject.data.feature_weather.remote.mapper.toDomain
import com.example.midtermproject.data.feature_weather.remote.service.WeatherWeeklyService
import com.example.midtermproject.domain.weather_feature.model.WeatherWeeklyInfo
import com.example.midtermproject.domain.weather_feature.repository.WeatherWeeklyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherWeeklyRepositoryImpl @Inject constructor(
    private val weatherWeeklyService: WeatherWeeklyService,
    private val handleResponse: HandleResponse
): WeatherWeeklyRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Flow<Resource<WeatherWeeklyInfo>> {
        return handleResponse.handleApiCall {
            weatherWeeklyService.getWeeklyWeatherData(lat, long)
        }.mapResource { dto ->
            dto.toDomain()
        }
    }
}