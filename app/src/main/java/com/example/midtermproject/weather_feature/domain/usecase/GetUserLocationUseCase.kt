package com.example.midtermproject.weather_feature.domain.usecase

import android.location.Location
import com.example.midtermproject.weather_feature.domain.repository.UserLocationRepository
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val userLocationRepository: UserLocationRepository
) {
    suspend operator fun invoke(): Location? {
        return userLocationRepository.getUserLocation()
    }
}