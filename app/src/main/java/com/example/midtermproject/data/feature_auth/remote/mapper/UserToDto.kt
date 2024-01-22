package com.example.midtermproject.data.feature_auth.remote.mapper

import com.example.midtermproject.data.feature_auth.remote.model.UserDto
import com.example.midtermproject.domain.feature_auth.model.User
fun User.toDto() : UserDto {
    return UserDto(email = email, password = password)
}