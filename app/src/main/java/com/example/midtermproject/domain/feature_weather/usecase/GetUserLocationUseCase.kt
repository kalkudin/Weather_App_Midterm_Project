package com.example.midtermproject.domain.feature_weather.usecase

import android.location.Location
import com.example.midtermproject.domain.feature_weather.repository.UserLocationRepository
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val userLocationRepository: UserLocationRepository
) {
    suspend operator fun invoke(): Location? {
        return userLocationRepository.getUserLocation()
    }
}