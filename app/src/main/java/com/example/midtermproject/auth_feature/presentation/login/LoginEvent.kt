package com.example.midtermproject.auth_feature.presentation.login

sealed class LoginEvent {
    data class LoginUser(val email : String , val password : String) : LoginEvent()
    data class LoginAndRememberUser(val email : String, val password : String) : LoginEvent()
    data object UserNotAuthenticatedYet : LoginEvent()
    data object TakeUserToHome : LoginEvent()
}