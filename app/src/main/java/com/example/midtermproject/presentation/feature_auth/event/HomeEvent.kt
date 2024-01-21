package com.example.midtermproject.presentation.auth_feature.event

sealed class HomeEvent {
    data object NavigateToLogin : HomeEvent()
    data object NavigateToRegister : HomeEvent()
}