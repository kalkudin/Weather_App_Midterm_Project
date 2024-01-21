package com.example.midtermproject.data.auth_feature.remote.mapper

import com.example.midtermproject.data.auth_feature.remote.model.UserDto
import com.example.midtermproject.domain.auth_feature.model.User
fun User.toDto() : UserDto {
    return UserDto(email = email, password = password)
}