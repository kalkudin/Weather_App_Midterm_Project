package com.example.midtermproject.domain.feature_weather.repository

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface CityLocationRepository {
    fun getCoordinates(cityName: String): Flow<Result<LatLng>>
}