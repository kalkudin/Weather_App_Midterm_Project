package com.example.midtermproject.auth_feature.data.mapper

import com.example.midtermproject.auth_feature.data.model.UserDto
import com.example.midtermproject.auth_feature.domain.model.User

fun User.toDto() : UserDto {
    return UserDto(email = email, password = password)
}