package com.example.midtermproject.domain.weather_feature.repository

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface CityLocationRepository {
    fun getCoordinates(cityName: String): Flow<Result<LatLng>>
}