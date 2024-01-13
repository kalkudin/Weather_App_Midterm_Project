package com.example.midtermproject.auth_feature.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.auth_feature.data.common.Resource
import com.example.midtermproject.auth_feature.domain.usecase.auth_usecase.LoginUserUseCase
import com.example.midtermproject.auth_feature.domain.usecase.datastore_usecase.SaveSessionUseCase
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
class LoginViewModel @Inject constructor(
    private val loginUserUseCase : LoginUserUseCase,
    private val saveSessionUseCase: SaveSessionUseCase
    ) : ViewModel(){

    private val _loginFlow = MutableStateFlow<Resource<Boolean>?>(null)
    val loginFlow: StateFlow<Resource<Boolean>?> = _loginFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<LoginNavigationEvent>()
    val navigationFlow : SharedFlow<LoginNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event : LoginEvent){
        when(event){
            is LoginEvent.LoginUser -> login(email = event.email, password = event.password, false)
            is LoginEvent.LoginAndRememberUser -> login(email = event.email, password = event.password, true)
            is LoginEvent.TakeUserToHome -> navigateUserToHome()
            is LoginEvent.UserNotAuthenticatedYet -> navigateUserToRegister()
        }
    }

    private fun login(email: String, password: String, shouldSessionBeSaved : Boolean) {
        viewModelScope.launch {
            saveSessionUseCase(shouldSessionBeSaved)

            loginUserUseCase(email, password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _loginFlow.value = Resource.Success(data = result.data)
                        loginSuccess()
                    }
                    is Resource.Error -> {
                        _loginFlow.value = Resource.Error(errorMessage = result.errorMessage)
                    }
                    is Resource.Loading -> {
                        _loginFlow.value = Resource.Loading
                    }
                }
            }
        }
    }

    private fun navigateUserToHome(){
        viewModelScope.launch {
            _navigationFlow.emit(LoginNavigationEvent.NavigateUserToHome)
        }
    }

    private fun navigateUserToRegister(){
        viewModelScope.launch {
            _navigationFlow.emit(LoginNavigationEvent.NavigateUserToRegister)
        }
    }

    private fun loginSuccess(){
        viewModelScope.launch {
            _navigationFlow.emit(LoginNavigationEvent.NavigateUserToMap)
        }
    }

}

sealed class LoginNavigationEvent {
    data object NavigateUserToRegister : LoginNavigationEvent()
    data object NavigateUserToHome : LoginNavigationEvent()
    data object NavigateUserToMap : LoginNavigationEvent()
}