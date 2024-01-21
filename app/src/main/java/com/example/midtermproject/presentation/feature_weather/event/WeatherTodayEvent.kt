package com.example.midtermproject.presentation.weather_feature.event

sealed class WeatherTodayEvent{
    data object LogOut : WeatherTodayEvent()
    data object NavigateToCityWeather : WeatherTodayEvent()
    data object MoveToDetailsPage : WeatherTodayEvent()
    data object RefreshPage : WeatherTodayEvent()
}
