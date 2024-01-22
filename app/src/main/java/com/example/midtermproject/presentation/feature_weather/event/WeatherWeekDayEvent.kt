package com.example.midtermproject.presentation.feature_weather.event

sealed class WeatherWeekDayEvent {
    data object NavigateToPreviousFragment : WeatherWeekDayEvent()
    data class LoadWeatherWithUserId(val id: Int) : WeatherWeekDayEvent()
}
