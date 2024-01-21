package com.example.midtermproject.domain.weather_feature.usecase

import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.domain.weather_feature.model.WeatherInfo
import com.example.midtermproject.domain.weather_feature.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, long: Double): Flow<Resource<WeatherInfo>> {
        return weatherRepository.getWeatherData(lat, long)
    }
}