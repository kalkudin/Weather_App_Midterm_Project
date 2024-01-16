package com.example.midtermproject.auth_feature.domain.repository

import com.example.midtermproject.auth_feature.data.remote.common.Resource
import com.example.midtermproject.auth_feature.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(user: User): Flow<Resource<Boolean>>
    suspend fun loginUser(user : User) : Flow<Resource<Boolean>>
}