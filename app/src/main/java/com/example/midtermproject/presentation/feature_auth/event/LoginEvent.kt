package com.example.midtermproject.presentation.feature_auth.event

sealed class LoginEvent {
    data class LoginUser(val email : String , val password : String) : LoginEvent()
    data class LoginAndRememberUser(val email : String, val password : String) : LoginEvent()
    data object UserNotAuthenticatedYet : LoginEvent()
    data object TakeUserToHome : LoginEvent()
    data object ResetFlow : LoginEvent()
}