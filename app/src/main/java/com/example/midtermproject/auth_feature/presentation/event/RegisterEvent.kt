package com.example.midtermproject.auth_feature.presentation.event

sealed class RegisterEvent {
    data class Register(val email : String, val password : String, val repeatPassword : String) : RegisterEvent()
    data object UserAlreadyAuthenticated : RegisterEvent()
    data object TakeUserToHome : RegisterEvent()
    data object ResetStateValue : RegisterEvent()
}