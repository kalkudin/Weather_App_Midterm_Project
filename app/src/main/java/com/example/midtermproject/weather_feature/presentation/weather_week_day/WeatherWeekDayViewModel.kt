package com.example.midtermproject.weather_feature.presentation.weather_week_day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermproject.weather_feature.domain.usecase.GetUserLocationUseCase
import com.example.midtermproject.weather_feature.presentation.event.WeatherWeekDayEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherWeekDayViewModel @Inject constructor(
    private val getUserLocationUseCase: GetUserLocationUseCase,
): ViewModel() {

    private val _navigationFlow = MutableSharedFlow<WeatherWeekDayNavigationEvent>()
    val navigationFlow : SharedFlow<WeatherWeekDayNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event : WeatherWeekDayEvent) {
        when(event) {
            is WeatherWeekDayEvent.NavigateToPreviousFragment -> navigateBack()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _navigationFlow.emit(WeatherWeekDayNavigationEvent.NavigateToPreviousFragment)
        }
    }
}

sealed class WeatherWeekDayNavigationEvent {
    data object NavigateToPreviousFragment : WeatherWeekDayNavigationEvent()
}