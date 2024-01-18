package com.example.midtermproject.weather_feature.data.remote.repository

import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.weather_feature.data.common.HandleResponse
import com.example.midtermproject.weather_feature.data.remote.mapper.mapResource
import com.example.midtermproject.weather_feature.data.remote.mapper.toDomain
import com.example.midtermproject.weather_feature.data.remote.service.WeatherWeeklyService
import com.example.midtermproject.weather_feature.domain.model.WeatherWeeklyInfo
import com.example.midtermproject.weather_feature.domain.repository.WeatherWeeklyRepository
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