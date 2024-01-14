package com.example.midtermproject.weather_feature.data.repository

import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.weather_feature.data.common.HandleResponse
import com.example.midtermproject.weather_feature.data.mapper.mapResource
import com.example.midtermproject.weather_feature.data.mapper.toDomain
import com.example.midtermproject.weather_feature.data.service.WeatherDataService
import com.example.midtermproject.weather_feature.domain.model.WeatherInfo
import com.example.midtermproject.weather_feature.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataService: WeatherDataService,
    private val handleResponse: HandleResponse
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Flow<Resource<WeatherInfo>> {
        return handleResponse.handleApiCall {
            weatherDataService.getWeatherData(lat, long)
        }.mapResource { dto ->
            dto.toDomain()
        }
    }
}