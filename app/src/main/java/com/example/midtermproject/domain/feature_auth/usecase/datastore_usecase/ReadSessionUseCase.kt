package com.example.midtermproject.domain.feature_auth.usecase.datastore_usecase

import com.example.midtermproject.data.feature_auth.local.repository.DataStoreRepositoryImpl
import com.example.midtermproject.domain.feature_auth.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    operator fun invoke(): Flow<Boolean> {
        return dataStoreRepository.readSession(DataStoreRepositoryImpl.SESSION_KEY)
    }
}