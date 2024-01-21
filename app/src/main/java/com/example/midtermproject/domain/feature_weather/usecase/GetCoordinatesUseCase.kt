package com.example.midtermproject.domain.weather_feature.usecase

import com.example.midtermproject.domain.weather_feature.repository.CityLocationRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoordinatesUseCase @Inject constructor(
    private val repository: CityLocationRepository
) {
    operator fun invoke(cityName: String): Flow<Result<LatLng>> {
        return repository.getCoordinates(cityName)
    }
}