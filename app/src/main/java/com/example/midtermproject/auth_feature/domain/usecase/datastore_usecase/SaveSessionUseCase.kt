package com.example.midtermproject.auth_feature.domain.usecase.datastore_usecase

import android.util.Log
import com.example.midtermproject.auth_feature.data.repository.DataStoreRepositoryImpl
import com.example.midtermproject.auth_feature.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(rememberMe: Boolean) {
            dataStoreRepository.saveSession(DataStoreRepositoryImpl.SESSION_KEY, rememberMe)
    }
}