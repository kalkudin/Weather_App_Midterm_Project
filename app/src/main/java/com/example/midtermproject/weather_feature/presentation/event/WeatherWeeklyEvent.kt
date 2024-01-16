package com.example.midtermproject.weather_feature.presentation.event

sealed class WeatherWeeklyEvent {
    data object NavigateBack : WeatherWeeklyEvent()
}