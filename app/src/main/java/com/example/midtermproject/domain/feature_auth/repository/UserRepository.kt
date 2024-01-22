package com.example.midtermproject.domain.feature_auth.repository

import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.domain.feature_auth.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(user: User): Flow<Resource<Boolean>>
    suspend fun loginUser(user : User) : Flow<Resource<Boolean>>
}