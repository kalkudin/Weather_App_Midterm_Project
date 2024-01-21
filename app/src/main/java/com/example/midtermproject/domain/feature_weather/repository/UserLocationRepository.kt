package com.example.midtermproject.domain.weather_feature.repository

import android.location.Location

interface UserLocationRepository {
    suspend fun getUserLocation(): Location?
}