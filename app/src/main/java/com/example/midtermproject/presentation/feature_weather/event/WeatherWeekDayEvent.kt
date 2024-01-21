package com.example.midtermproject.presentation.weather_feature.event

sealed class WeatherWeekDayEvent {
    data object NavigateToPreviousFragment : WeatherWeekDayEvent()
    data class LoadWeatherWithUserId(val id: Int) : WeatherWeekDayEvent()
}
