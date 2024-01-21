package com.example.midtermproject.presentation.weather_feature.event

sealed class WeatherCityEvent {
    data object NavigateBack : WeatherCityEvent()
    data class GetWeatherForCity(val city : String) : WeatherCityEvent()
    data class WeatherItemClicked(val id : Int) : WeatherCityEvent()
}
