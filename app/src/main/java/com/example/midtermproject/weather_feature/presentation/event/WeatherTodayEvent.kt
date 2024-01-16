package com.example.midtermproject.weather_feature.presentation.event

sealed class WeatherTodayEvent{
    data object LogOut : WeatherTodayEvent()
    data object MoveToDetailsPage : WeatherTodayEvent()
    data object RefreshPage : WeatherTodayEvent()
}
