package com.example.midtermproject.auth_feature.di

import com.example.midtermproject.auth_feature.data.common.HandleAuthentication
import com.example.midtermproject.auth_feature.data.repository.UserRepositoryImpl
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
}