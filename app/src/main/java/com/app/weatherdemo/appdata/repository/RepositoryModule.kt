package com.app.weatherdemo.appdata.repository

import com.app.weatherdemo.appdata.api.ResponseState
import com.app.weatherdemo.appdata.db.WeatherEntity
import com.app.weatherdemo.appdata.model.*

interface RepositoryModule {
    //weather from server
    suspend fun getWeatherByLocation(coord: Coord, refresh:Boolean): ResponseState<WeatherAPIResponse?>
    suspend fun getWeatherByZipAndCountry(zipAndCountry: ZipCountryCode): ResponseState<WeatherAPIResponse?>
    suspend fun getWeatherByCityName(name: String): ResponseState<WeatherAPIResponse?>
    suspend fun getWeatherByCityId(id: Int): ResponseState<WeatherAPIResponse?>

    //single call response from API
    suspend fun getOneCallWeatherForecast(coord: Coord, units: String): ResponseState<ResponseSingleWeatherForecast?>

    // forecast data
    suspend fun getWeatherForecastByCityName(name: String): ResponseState<List<NetworkWeatherForecast>?>
    suspend fun getWeatherForecastByLocation(coord: Coord,refresh: Boolean): ResponseState<List<NetworkWeatherForecast>?>

    // local data
    suspend fun getWeatherFromDatabase(): WeatherAPIResponse?
    suspend fun saveWeatherToDatabase(weather: WeatherAPIResponse?):Unit
    suspend fun deleteWeatherFromDatabase(weather: WeatherEntity?):Unit
    suspend fun deleteAllWeatherFromDatabase()
    suspend fun getAllWeatherFromDatabase():List<WeatherEntity>

}