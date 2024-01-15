package com.example.midtermproject.auth_feature.presentation.model

data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
