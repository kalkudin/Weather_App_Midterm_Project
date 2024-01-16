package com.example.midtermproject.weather_feature.presentation.waether_weekly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.weather_feature.presentation.event.WeatherWeeklyEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherWeeklyViewModel @Inject constructor(): ViewModel() {

    private val _navigationFlow = MutableSharedFlow<WeatherWeeklyNavigationEvent>()
    val navigationFlow : SharedFlow<WeatherWeeklyNavigationEvent> = _navigationFlow.asSharedFlow()

     fun onEvent(event : WeatherWeeklyEvent) {
         when(event) {
             is WeatherWeeklyEvent.NavigateBack -> navigateBack()
         }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _navigationFlow.emit(WeatherWeeklyNavigationEvent.NavigateBackToDaily)
        }
    }


}

sealed class WeatherWeeklyNavigationEvent(){
    data object NavigateBackToDaily : WeatherWeeklyNavigationEvent()
}