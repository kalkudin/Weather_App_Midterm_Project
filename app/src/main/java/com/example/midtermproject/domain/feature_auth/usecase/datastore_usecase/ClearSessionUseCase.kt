package com.example.midtermproject.domain.feature_auth.usecase.datastore_usecase

import com.example.midtermproject.domain.feature_auth.repository.DataStoreRepository
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(){
        dataStoreRepository.clear()
    }
}