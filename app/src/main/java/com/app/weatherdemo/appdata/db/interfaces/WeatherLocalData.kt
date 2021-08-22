package com.app.weatherdemo.appdata.db.interfaces

import com.app.weatherdemo.appdata.db.WeatherEntity

interface WeatherLocalData   {
    suspend fun getWeather(): WeatherEntity?
    suspend fun saveWeather(weather: WeatherEntity)
    suspend fun deleteWeather(weather: WeatherEntity)
    suspend fun deleteAllWeahter()
    suspend fun getAllWeather(): List<WeatherEntity>
/*
    suspend fun deleteWeatherByCityName(city_name:String)
*/
}