package com.app.weatherdemo.appdata.api

import com.app.weatherdemo.appdata.model.ResponseSingleWeatherForecast
import com.app.weatherdemo.appdata.model.WeatherAPIResponse
import com.app.weatherdemo.appdata.model.WeatherForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MobiWeatherAPI {

    @GET("forecast")
    suspend fun getWeatherForecastByCityName(
        @Query("q")
        q: String,
    ): Response<WeatherForecastResponse>

    @GET("forecast")
    suspend fun getWeatherForecastByLocation(
        @Query("lat")
        latitude: String,
        @Query("lon")
        longitude: String
    ): Response<WeatherForecastResponse>

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q")
        q: String,
    ): Response<WeatherAPIResponse>

    @GET("weather")
    suspend fun getWeatherByZipAndContryCode(
        @Query("zip")
        zip: String,
    ): Response<WeatherAPIResponse>

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat")
        latitude: String,
        @Query("lon")
        longitude: String
    ): Response<WeatherAPIResponse>

    @GET("weather")
    suspend fun getWeatherByCityID(
        @Query("id")
        query: String
    ): Response<WeatherAPIResponse>

    @GET("onecall")
    suspend fun getWeatherForecast(
        @Query("lat")
        latitude: String,
        @Query("lon")
        longitude: String,
        @Query("units")
        units: String
    ): Response<ResponseSingleWeatherForecast>

}