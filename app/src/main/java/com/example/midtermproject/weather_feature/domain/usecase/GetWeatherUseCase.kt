package com.example.midtermproject.weather_feature.domain.usecase

import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.weather_feature.domain.model.WeatherInfo
import com.example.midtermproject.weather_feature.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, long: Double): Flow<Resource<WeatherInfo>> {
        return weatherRepository.getWeatherData(lat, long)
    }
}