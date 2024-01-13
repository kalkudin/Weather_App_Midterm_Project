package com.example.midtermproject.auth_feature.presentation.home

sealed class HomeEvent {
    data object NavigateToLogin : HomeEvent()
    data object NavigateToRegister : HomeEvent()
}