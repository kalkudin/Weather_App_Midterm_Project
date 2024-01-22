package com.example.midtermproject.domain.feature_weather.usecase

import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.domain.feature_weather.model.WeatherInfo
import com.example.midtermproject.domain.feature_weather.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, long: Double): Flow<Resource<WeatherInfo>> {
        return weatherRepository.getWeatherData(lat, long)
    }
}