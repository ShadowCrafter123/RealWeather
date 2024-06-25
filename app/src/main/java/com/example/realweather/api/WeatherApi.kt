package com.example.realweather.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apikey: String,
        @Query("q") city: String
    ): Response<WeatherModel>
}

// Response é uma classe da biblioteca Retrofit que guarda a resposta HTTP recebida do servidor.