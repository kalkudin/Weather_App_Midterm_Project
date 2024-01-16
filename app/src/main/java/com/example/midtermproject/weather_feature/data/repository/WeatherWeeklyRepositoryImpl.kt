package com.example.midtermproject.weather_feature.data.repository

import com.example.midtermproject.auth_feature.data.remote.common.Resource
import com.example.midtermproject.weather_feature.data.common.HandleResponse
import com.example.midtermproject.weather_feature.data.mapper.mapResource
import com.example.midtermproject.weather_feature.data.mapper.toDomainModel
import com.example.midtermproject.weather_feature.data.service.WeatherWeeklyService
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
            dto.toDomainModel()
        }
    }
}