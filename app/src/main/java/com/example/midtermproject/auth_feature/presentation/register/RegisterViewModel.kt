package com.example.midtermproject.auth_feature.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.auth_feature.domain.usecase.auth_usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUserUseCase: RegisterUserUseCase) : ViewModel() {

    private val _registerFlow = MutableStateFlow<Resource<Boolean>?>(null)
    val registerFlow: StateFlow<Resource<Boolean>?> = _registerFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<RegisterNavigationEvent>()
    val navigationFlow : SharedFlow<RegisterNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event : RegisterEvent) {
        viewModelScope.launch {
            when(event){
                is RegisterEvent.Register -> registerUser(email = event.email, password = event.password, repeatPassword = event.repeatPassword)
                is RegisterEvent.UserAlreadyAuthenticated -> takeUserToLogin()
                is RegisterEvent.TakeUserToHome -> takeUserBackToHome()
            }
        }
    }

    private suspend fun registerUser(email : String, password : String, repeatPassword : String){
        viewModelScope.launch {
            registerUserUseCase(email = email, password = password, repeatPassword = repeatPassword).collect(){
                when(it){
                    is Resource.Success -> {
                        _registerFlow.value = it
                        takeUserToLogin()
                    }

                    is Resource.Error -> {
                        _registerFlow.value = Resource.Error(it.errorMessage)
                    }

                    is Resource.Loading -> {
                        _registerFlow.value = Resource.Loading
                    }
                }
            }
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