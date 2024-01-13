package com.example.midtermproject.auth_feature.domain.usecase.datastore_usecase

import com.example.midtermproject.auth_feature.domain.repository.DataStoreRepository
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(){
        dataStoreRepository.clear()
    }
}