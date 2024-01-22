package com.example.midtermproject.presentation.feature_weather.event

sealed class WeatherWeeklyEvent {
    data object NavigateToPreviousFragment : WeatherWeeklyEvent()
    data class ItemClicked(val id : Int) : WeatherWeeklyEvent()
}