package com.example.midtermproject.weather_feature.presentation.event

sealed class WeatherWeekDayEvent {
    data object NavigateToPreviousFragment : WeatherWeekDayEvent()
    data class LoadWeatherWithUserId(val id: Int) : WeatherWeekDayEvent()
}
