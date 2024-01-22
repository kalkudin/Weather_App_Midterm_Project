package com.example.midtermproject.domain.feature_weather.usecase

import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.domain.feature_weather.model.WeatherWeeklyInfo
import com.example.midtermproject.domain.feature_weather.repository.WeatherWeeklyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class GetWeeklyWeatherUseCase @Inject constructor(private val weatherWeeklyRepository: WeatherWeeklyRepository){
    suspend operator fun invoke(lat : Double, long : Double) : Flow<Resource<WeatherWeeklyInfo>> {
        return weatherWeeklyRepository.getWeatherData(lat = lat, long = long)
    }
}