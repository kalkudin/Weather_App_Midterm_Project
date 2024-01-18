package com.example.midtermproject.weather_feature.data.remote.service

import com.example.midtermproject.weather_feature.data.remote.model.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDataService {
    @GET("v1/forecast?hourly=temperature_2m,weathercode,windspeed_10m,relative_humidity_2m,")
    suspend fun getWeatherData(@Query("latitude") lat : Double, @Query ("longitude") long : Double) : Response<WeatherDto>
}