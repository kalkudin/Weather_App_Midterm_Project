package com.example.midtermproject.domain.feature_auth.usecase.auth_usecase

import com.example.midtermproject.data.common.Resource
import com.example.midtermproject.domain.feature_auth.model.User
import com.example.midtermproject.domain.feature_auth.repository.UserRepository
import com.example.midtermproject.domain.feature_auth.util.AuthenticationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authUtil: AuthenticationUtil
) {
    suspend operator fun invoke(email : String, password : String) : Flow<Resource<Boolean>> {
        if (!authUtil.areFieldsNotEmpty(email, password)) {
            return flowOf(Resource.Error("Please fill in all fields"))
        }

        if(!authUtil.isValidEmail(email)){
            return flowOf(Resource.Error("Invalid email address"))
        }

        if (!authUtil.isValidPassword(password)) {
            return flowOf(Resource.Error("Password does not meet the criteria"))
        }

        val user = User(email = email, password = password)
        return userRepository.loginUser(user = user)
    }
}