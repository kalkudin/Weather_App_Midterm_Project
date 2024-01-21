package com.example.midtermproject.presentation.weather_feature.event

sealed class WeatherWeeklyEvent {
    data object NavigateToPreviousFragment : WeatherWeeklyEvent()
    data class ItemClicked(val id : Int) : WeatherWeeklyEvent()
}