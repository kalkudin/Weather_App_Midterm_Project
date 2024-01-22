package com.example.midtermproject.data.feature_auth.remote.repository

import com.example.midtermproject.data.common.HandleAuthentication
import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.data.feature_auth.remote.mapper.toDto
import com.example.midtermproject.domain.feature_auth.model.User
import com.example.midtermproject.domain.feature_auth.repository.UserRepository
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