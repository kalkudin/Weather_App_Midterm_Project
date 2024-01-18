package com.example.midtermproject.auth_feature.data.common

import com.example.midtermproject.auth_feature.data.remote.model.UserDto
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HandleAuthentication @Inject constructor() {

    fun <T> authenticate(user: UserDto, operation: (String, String) -> Task<AuthResult>, onSuccess: (AuthResult) -> T
    ): Flow<Resource<T>> = flow {
        emit(Resource.Loading)
        try {
            val result = operation(user.email, user.password).await()
            if (result.user != null) {
                emit(Resource.Success(onSuccess(result)))
            } else {
                emit(Resource.Error("Authentication failed"))
            }
        } catch (e: FirebaseAuthWeakPasswordException) {
            emit(Resource.Error("The password is too weak"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(Resource.Error("Invalid credentials"))
        } catch (e: FirebaseAuthUserCollisionException) {
            emit(Resource.Error("User already exists"))
        } catch (e: FirebaseAuthInvalidUserException) {
            emit(Resource.Error("User not found"))
        } catch (e: FirebaseAuthEmailException) {
            emit(Resource.Error("Email error"))
        } catch (e: FirebaseNetworkException) {
            emit(Resource.Error("Network error"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}