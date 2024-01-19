package com.example.midtermproject.weather_feature.presentation.event

sealed class WeatherWeeklyEvent {
    data object NavigateToPreviousFragment : WeatherWeeklyEvent()
    data class ItemClicked(val id : Int) : WeatherWeeklyEvent()
}