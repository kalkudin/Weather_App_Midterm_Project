package com.example.midtermproject.presentation.feature_auth.event

sealed class HomeEvent {
    data object NavigateToLogin : HomeEvent()
    data object NavigateToRegister : HomeEvent()
}