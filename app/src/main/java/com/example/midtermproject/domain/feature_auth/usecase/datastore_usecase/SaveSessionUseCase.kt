package com.example.midtermproject.domain.feature_auth.usecase.datastore_usecase

import com.example.midtermproject.data.auth_feature.local.repository.DataStoreRepositoryImpl
import com.example.midtermproject.domain.feature_auth.repository.DataStoreRepository
import javax.inject.Inject

class SaveSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(rememberMe: Boolean) {
            dataStoreRepository.saveSession(DataStoreRepositoryImpl.SESSION_KEY, rememberMe)
    }
}