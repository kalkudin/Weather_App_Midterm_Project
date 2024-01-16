package com.example.midtermproject.weather_feature.data.service

import com.example.midtermproject.weather_feature.data.model.WeatherWeeklyDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherWeeklyService {
    @GET("v1/forecast?hourly=temperature_2m,weathercode,")
    suspend fun getWeeklyWeatherData(@Query("latitude") lat: Double, @Query("longitude") long: Double): Response<WeatherWeeklyDto>
}