package com.example.midtermproject.presentation.feature_weather.event

sealed class WeatherCityEvent {
    data object NavigateBack : WeatherCityEvent()
    data class GetWeatherForCity(val city : String) : WeatherCityEvent()
    data class WeatherItemClicked(val id : Int) : WeatherCityEvent()
}
