package com.example.midtermproject.auth_feature.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.midtermproject.auth_feature.data.local.repository.DataStoreRepositoryImpl
import com.example.midtermproject.auth_feature.data.common.HandleAuthentication
import com.example.midtermproject.auth_feature.data.remote.repository.UserRepositoryImpl
import com.example.midtermproject.auth_feature.domain.repository.DataStoreRepository
import com.example.midtermproject.auth_feature.domain.repository.UserRepository
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
}