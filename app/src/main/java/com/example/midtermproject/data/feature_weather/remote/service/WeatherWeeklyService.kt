package com.example.midtermproject.data.feature_weather.remote.service

import com.example.midtermproject.data.feature_weather.remote.model.WeatherWeeklyDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherWeeklyService {
    @GET("v1/forecast?hourly=temperature_2m,weathercode,")
    suspend fun getWeeklyWeatherData(@Query("latitude") lat: Double, @Query("longitude") long: Double): Response<WeatherWeeklyDto>
}