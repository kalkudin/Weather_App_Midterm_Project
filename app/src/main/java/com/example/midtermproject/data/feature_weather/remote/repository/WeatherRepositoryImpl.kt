package com.example.midtermproject.data.feature_weather.remote.repository

import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.data.common.HandleResponse
import com.example.midtermproject.data.feature_weather.remote.mapper.mapResource
import com.example.midtermproject.data.feature_weather.remote.mapper.toDomain
import com.example.midtermproject.data.feature_weather.remote.service.WeatherDataService
import com.example.midtermproject.domain.weather_feature.model.WeatherInfo
import com.example.midtermproject.domain.weather_feature.repository.WeatherRepository
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