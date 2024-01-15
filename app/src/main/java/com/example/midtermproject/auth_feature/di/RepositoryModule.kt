package com.example.midtermproject.auth_feature.di

import android.app.Application
import com.example.midtermproject.auth_feature.data.common.HandleAuthentication
import com.example.midtermproject.auth_feature.data.repository.UserRepositoryImpl
import com.example.midtermproject.auth_feature.domain.repository.UserRepository
import com.example.midtermproject.weather_feature.data.common.HandleResponse
import com.example.midtermproject.weather_feature.data.repository.UserLocationRepositoryImpl
import com.example.midtermproject.weather_feature.data.repository.WeatherRepositoryImpl
import com.example.midtermproject.weather_feature.data.service.WeatherDataService
import com.example.midtermproject.weather_feature.domain.repository.UserLocationRepository
import com.example.midtermproject.weather_feature.domain.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideUserRepository(firebaseAuth: FirebaseAuth, handleAuthentication: HandleAuthentication): UserRepository =
        UserRepositoryImpl(handleAuthentication = handleAuthentication, firebaseAuth = firebaseAuth)

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherDataService: WeatherDataService, handleResponse: HandleResponse) : WeatherRepository {
        return WeatherRepositoryImpl(weatherDataService = weatherDataService, handleResponse = handleResponse)
    }

    @Provides
    @Singleton
    fun provideUserLocationRepository(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): UserLocationRepository {
        return UserLocationRepositoryImpl(fusedLocationProviderClient, application)
    }
}