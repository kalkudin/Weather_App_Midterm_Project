package com.example.midtermproject.auth_feature.presentation.event

sealed class HomeEvent {
    data object NavigateToLogin : HomeEvent()
    data object NavigateToRegister : HomeEvent()
}