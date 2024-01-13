package com.example.midtermproject.auth_feature.data.repository

import com.example.midtermproject.auth_feature.data.common.HandleAuthentication
import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.auth_feature.data.mapper.toDto
import com.example.midtermproject.auth_feature.domain.model.User
import com.example.midtermproject.auth_feature.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val handleAuthentication: HandleAuthentication,
    private val firebaseAuth: FirebaseAuth
) : UserRepository {

    override suspend fun registerUser(user: User): Flow<Resource<Boolean>> {
        return handleAuthentication.authenticate(
            user.toDto(),
            firebaseAuth::createUserWithEmailAndPassword,
            onSuccess = { true }
        )
    }

    override suspend fun loginUser(user: User): Flow<Resource<Boolean>> {
        return handleAuthentication.authenticate(
            user.toDto(),
            firebaseAuth::signInWithEmailAndPassword,
            onSuccess = { true }
        )
    }
}