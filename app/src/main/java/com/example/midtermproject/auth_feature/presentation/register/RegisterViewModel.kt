package com.example.midtermproject.auth_feature.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.auth_feature.domain.usecase.auth_usecase.RegisterUserUseCase
import com.example.midtermproject.auth_feature.presentation.event.RegisterEvent
import com.example.midtermproject.auth_feature.presentation.model.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUserUseCase: RegisterUserUseCase) : ViewModel() {

    private val _registerFlow = MutableStateFlow(AuthState())
    val registerFlow:  StateFlow<AuthState> = _registerFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<RegisterNavigationEvent>()
    val navigationFlow : SharedFlow<RegisterNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event : RegisterEvent) {
        viewModelScope.launch {
            when(event){
                is RegisterEvent.Register -> registerUser(email = event.email, password = event.password, repeatPassword = event.repeatPassword)
                is RegisterEvent.UserAlreadyAuthenticated -> takeUserToLogin()
                is RegisterEvent.TakeUserToHome -> takeUserBackToHome()
                is RegisterEvent.ResetStateValue -> resetRegisterFlow()
            }
        }
    }

    private fun registerUser(email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
            _registerFlow.update { it.copy(isLoading = true) }
            registerUserUseCase(email, password, repeatPassword).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _registerFlow.update { it.copy(isSuccess = true, isLoading = false) }
                        takeUserToLogin()
                    }
                    is Resource.Error -> {
                        _registerFlow.update { it.copy(errorMessage = resource.errorMessage, isLoading = false) }
                    }
                    is Resource.Loading -> {
                        _registerFlow.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun resetRegisterFlow(){
        viewModelScope.launch {
            _registerFlow.value = AuthState()
        }
    }

    private suspend fun takeUserToLogin(){
        _navigationFlow.emit(RegisterNavigationEvent.NavigateToLogin)
    }

    private suspend fun takeUserBackToHome(){
        _navigationFlow.emit(RegisterNavigationEvent.NavigateToHome)
    }
}

sealed class RegisterNavigationEvent {
    data object NavigateToHome : RegisterNavigationEvent()
    data object NavigateToLogin : RegisterNavigationEvent()
}