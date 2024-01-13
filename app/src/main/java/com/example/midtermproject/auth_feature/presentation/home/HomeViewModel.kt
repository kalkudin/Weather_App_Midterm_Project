package com.example.midtermproject.auth_feature.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.auth_feature.domain.usecase.datastore_usecase.ReadSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val readSessionUseCase: ReadSessionUseCase
) : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<NavigationEvent>()
    val navigationFlow: SharedFlow<NavigationEvent> = _navigationFlow.asSharedFlow()

    init {
        checkSession()
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.NavigateToLogin -> navigateToLoginPage()
                is HomeEvent.NavigateToRegister -> navigateToRegister()
            }
        }
    }

    private fun checkSession(){
        viewModelScope.launch {
            readSessionUseCase().collect {
                when(it){
                    true -> _navigationFlow.emit(NavigationEvent.NavigateToLogin)
                    false -> _navigationFlow.emit(NavigationEvent.RemainOnCurrentPage)
                }
            }
        }
    }

    private suspend fun navigateToLoginPage(){
        _navigationFlow.emit(NavigationEvent.NavigateToLogin)
    }

    private suspend fun navigateToRegister(){
        _navigationFlow.emit(NavigationEvent.NavigateToRegister)
    }
}

sealed class NavigationEvent() {
    data object NavigateToLogin : NavigationEvent()
    data object NavigateToRegister : NavigationEvent()
    data object NavigateToMap : NavigationEvent()
    data object RemainOnCurrentPage : NavigationEvent()
}