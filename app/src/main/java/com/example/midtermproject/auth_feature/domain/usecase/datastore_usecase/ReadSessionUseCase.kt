package com.example.midtermproject.auth_feature.domain.usecase.datastore_usecase

import com.example.midtermproject.auth_feature.data.local.repository.DataStoreRepositoryImpl
import com.example.midtermproject.auth_feature.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    operator fun invoke(): Flow<Boolean> {
        return dataStoreRepository.readSession(DataStoreRepositoryImpl.SESSION_KEY)
    }
}