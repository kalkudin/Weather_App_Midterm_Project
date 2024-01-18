package com.example.midtermproject.weather_feature.data.remote.repository

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.midtermproject.weather_feature.domain.repository.UserLocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserLocationRepositoryImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application
) : UserLocationRepository {
    override suspend fun getUserLocation(): Location? {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFineLocationPermission && !hasCoarseLocationPermission) {
            Log.d("UserLocationRepository", "No location permission")
            return null
        }

        return try {
            val lastLocation = fusedLocationProviderClient.lastLocation.await()
            Log.d("UserLocationRepository", "Location obtained: $lastLocation")
            return lastLocation
        } catch (e: Exception) {
            Log.e("UserLocationRepository", "Error getting location: ${e.message}", e)
            null
        }
    }
}