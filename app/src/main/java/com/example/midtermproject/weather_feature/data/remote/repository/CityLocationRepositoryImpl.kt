package com.example.midtermproject.weather_feature.data.remote.repository

import android.location.Geocoder
import android.util.Log
import com.example.midtermproject.weather_feature.domain.repository.CityLocationRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class CityLocationRepositoryImpl(private val geocoder: Geocoder) : CityLocationRepository {

    override fun getCoordinates(cityName: String): Flow<Result<LatLng>> = flow {
        try {
            Log.d("Geocoding", "Attempting to geocode city: $cityName")

            val result = suspendCancellableCoroutine<Result<LatLng>> { continuation ->
                val lowerLeftLatitude = -90.0
                val lowerLeftLongitude = -180.0
                val upperRightLatitude = 90.0
                val upperRightLongitude = 180.0

                geocoder.getFromLocationName(
                    cityName, 1, lowerLeftLatitude, lowerLeftLongitude, upperRightLatitude, upperRightLongitude) { addresses ->
                    if (addresses.isNotEmpty()) {
                        val location = addresses.first()
                        Log.d("Geocoding", "Geocoding success for $cityName: Lat ${location.latitude}, Lng ${location.longitude}")
                        continuation.resume(Result.success(LatLng(location.latitude, location.longitude)))
                    } else {
                        Log.d("Geocoding", "Geocoding failed: Location not found for $cityName")
                        continuation.resume(Result.failure(Exception("Location not found")))
                    }
                }
            }

            emit(result)
        } catch (e: Exception) {
            Log.e("Geocoding", "Geocoding error for $cityName", e)
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}