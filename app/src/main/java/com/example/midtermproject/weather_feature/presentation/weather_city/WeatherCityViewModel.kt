package com.example.midtermproject.weather_feature.presentation.weather_city

import androidx.lifecycle.ViewModel
import com.example.midtermproject.weather_feature.presentation.event.WeatherCityEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherCityViewModel @Inject constructor() : ViewModel() {

    fun onEvent(event : WeatherCityEvent) {

    }
}