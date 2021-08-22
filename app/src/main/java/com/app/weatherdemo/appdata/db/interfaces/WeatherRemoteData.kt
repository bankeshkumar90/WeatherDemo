package com.app.weatherdemo.appdata.db.interfaces

import com.app.weatherdemo.appdata.api.ResponseState
import com.app.weatherdemo.appdata.model.*

interface WeatherRemoteData {
    //weather
    suspend fun getWeatherByLocation(coord: Coord): ResponseState<WeatherAPIResponse?>
    suspend fun getWeatherByZipAndCountry(zipAndCountry: ZipCountryCode): ResponseState<WeatherAPIResponse?>
    suspend fun getWeatherByCityName(name: String): ResponseState<WeatherAPIResponse?>
    suspend fun getWeatherByCityId(id: Int): ResponseState<WeatherAPIResponse?>

    //oneCall
    suspend fun getOneCallWeatherForecast(coord: Coord, units: String): ResponseState<ResponseSingleWeatherForecast?>

    // forecast
    suspend fun getWeatherForecastByCityName(name: String): ResponseState<List<NetworkWeatherForecast>?>
    suspend fun getWeatherForecastByLocation(coord: Coord): ResponseState<List<NetworkWeatherForecast>?>

}