package com.example.midtermproject.auth_feature.data.remote.mapper

import com.example.midtermproject.auth_feature.data.remote.model.UserDto
import com.example.midtermproject.auth_feature.domain.model.User

fun User.toDto() : UserDto {
    return UserDto(email = email, password = password)
}