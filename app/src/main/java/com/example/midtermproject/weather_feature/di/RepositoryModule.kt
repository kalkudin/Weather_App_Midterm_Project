package com.example.midtermproject.weather_feature.di

import android.app.Application
import com.example.midtermproject.weather_feature.data.common.HandleResponse
import com.example.midtermproject.weather_feature.data.remote.repository.UserLocationRepositoryImpl
import com.example.midtermproject.weather_feature.data.remote.repository.WeatherRepositoryImpl
import com.example.midtermproject.weather_feature.data.remote.repository.WeatherWeeklyRepositoryImpl
import com.example.midtermproject.weather_feature.data.remote.service.WeatherDataService
import com.example.midtermproject.weather_feature.data.remote.service.WeatherWeeklyService
import com.example.midtermproject.weather_feature.domain.repository.UserLocationRepository
import com.example.midtermproject.weather_feature.domain.repository.WeatherRepository
import com.example.midtermproject.weather_feature.domain.repository.WeatherWeeklyRepository
import com.google.android.gms.location.FusedLocationProviderClient
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
    fun provideWeatherRepository(weatherDataService: WeatherDataService, handleResponse: HandleResponse) : WeatherRepository {
        return WeatherRepositoryImpl(weatherDataService = weatherDataService, handleResponse = handleResponse)
    }
    
    @Singleton
    @Provides
    fun provideWeatherWeeklyRepository(weatherWeeklyService: WeatherWeeklyService, handleResponse: HandleResponse) : WeatherWeeklyRepository {
        return WeatherWeeklyRepositoryImpl(weatherWeeklyService = weatherWeeklyService, handleResponse = handleResponse)
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