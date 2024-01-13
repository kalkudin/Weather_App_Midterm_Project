package com.example.midtermproject.auth_feature.presentation.register

sealed class RegisterEvent {
    data class Register(val email : String, val password : String, val repeatPassword : String) : RegisterEvent()
    data object UserAlreadyAuthenticated : RegisterEvent()
    data object TakeUserToHome : RegisterEvent()
}