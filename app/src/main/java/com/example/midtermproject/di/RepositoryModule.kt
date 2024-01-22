package com.example.midtermproject.di

import android.app.Application
import android.location.Geocoder
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.midtermproject.data.common.HandleAuthentication
import com.example.midtermproject.data.common.HandleResponse
import com.example.midtermproject.data.feature_auth.local.repository.DataStoreRepositoryImpl
import com.example.midtermproject.data.feature_auth.remote.repository.UserRepositoryImpl
import com.example.midtermproject.data.feature_weather.remote.repository.CityLocationRepositoryImpl
import com.example.midtermproject.data.feature_weather.remote.repository.UserLocationRepositoryImpl
import com.example.midtermproject.data.feature_weather.remote.repository.WeatherRepositoryImpl
import com.example.midtermproject.data.feature_weather.remote.repository.WeatherWeeklyRepositoryImpl
import com.example.midtermproject.data.feature_weather.remote.service.WeatherDataService
import com.example.midtermproject.data.feature_weather.remote.service.WeatherWeeklyService
import com.example.midtermproject.domain.feature_auth.repository.DataStoreRepository
import com.example.midtermproject.domain.feature_auth.repository.UserRepository
import com.example.midtermproject.domain.feature_weather.repository.CityLocationRepository
import com.example.midtermproject.domain.feature_weather.repository.UserLocationRepository
import com.example.midtermproject.domain.feature_weather.repository.WeatherRepository
import com.example.midtermproject.domain.feature_weather.repository.WeatherWeeklyRepository
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

    @Provides
    @Singleton
    fun provideUserDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore)
    }

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
    fun provideUserLocationRepository(fusedLocationProviderClient: FusedLocationProviderClient, application: Application): UserLocationRepository {
        return UserLocationRepositoryImpl(fusedLocationProviderClient, application)
    }

    @Provides
    @Singleton
    fun provideCityLocationRepository(geocoder: Geocoder): CityLocationRepository {
        return CityLocationRepositoryImpl(geocoder)
    }
}