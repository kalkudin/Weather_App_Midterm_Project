package com.example.midtermproject.weather_feature.domain.repository

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface CityLocationRepository {
    fun getCoordinates(cityName: String): Flow<Result<LatLng>>
}