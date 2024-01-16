package com.example.midtermproject.weather_feature.domain.usecase

import com.example.midtermproject.auth_feature.data.remote.common.Resource
import com.example.midtermproject.weather_feature.domain.model.WeatherWeeklyInfo
import com.example.midtermproject.weather_feature.domain.repository.WeatherWeeklyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class GetWeeklyWeatherUseCase @Inject constructor(private val weatherWeeklyRepository: WeatherWeeklyRepository){
    suspend operator fun invoke(lat : Double, long : Double) : Flow<Resource<WeatherWeeklyInfo>> {
        return weatherWeeklyRepository.getWeatherData(lat = lat, long = long)
    }
}