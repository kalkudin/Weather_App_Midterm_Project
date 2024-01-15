package com.example.midtermproject.weather_feature.domain.repository

import android.location.Location

interface UserLocationRepository {
    suspend fun getUserLocation(): Location?
}