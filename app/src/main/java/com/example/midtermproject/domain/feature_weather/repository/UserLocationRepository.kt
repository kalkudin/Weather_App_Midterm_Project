package com.example.midtermproject.domain.feature_weather.repository

import android.location.Location

interface UserLocationRepository {
    suspend fun getUserLocation(): Location?
}