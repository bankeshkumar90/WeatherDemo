package com.app.weatherdemo.appdata.repository

import com.app.weatherdemo.appdata.api.ResponseState
import com.app.weatherdemo.appdata.db.WeatherEntity
import com.app.weatherdemo.appdata.db.interfaces.WeatherLocalData
import com.app.weatherdemo.appdata.db.interfaces.WeatherRemoteData
import com.app.weatherdemo.appdata.model.*
import com.weather.weatherify.mappers.WeatherMapperLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MobiWeatherRepository @Inject constructor(
    private val weatherRemoteData: WeatherRemoteData,
    private val weatherLocalData: WeatherLocalData
) : RepositoryModule {

    override suspend fun getWeatherByLocation(
        coord: Coord,
        refresh: Boolean
    ): ResponseState<WeatherAPIResponse?> = withContext(Dispatchers.IO) {
        if (refresh) {
            when (val apiResponse = weatherRemoteData.getWeatherByLocation(coord)) {
                is ResponseState.Success -> {
                    ResponseState.Success(apiResponse.data)
                }
                is ResponseState.Error -> {
                    if (apiResponse.message != null)
                        ResponseState.Error(apiResponse.message)
                    else
                        ResponseState.Error("Some error occured!")
                }
                else -> ResponseState.Loading()
            }
        } else {
            val mapper = WeatherMapperLocal()
            val forecast = weatherLocalData.getWeather()
            if (forecast != null) {
                ResponseState.Success(mapper.transformToDomain(forecast))
            } else {
                ResponseState.Success(null)
            }
        }
    }

    override suspend fun getWeatherByZipAndCountry(zipAndCountry: ZipCountryCode): ResponseState<WeatherAPIResponse?> =
        withContext(Dispatchers.IO) {
            weatherRemoteData.getWeatherByZipAndCountry(zipAndCountry)
        }

    override suspend fun getWeatherByCityName(cityName: String): ResponseState<WeatherAPIResponse?> =
        withContext(Dispatchers.IO) {
            weatherRemoteData.getWeatherByCityName(cityName)
        }

    override suspend fun getWeatherByCityId(cityId: Int): ResponseState<WeatherAPIResponse?> =
        withContext(Dispatchers.IO) {
            weatherRemoteData.getWeatherByCityId(cityId)
        }

    override suspend fun getOneCallWeatherForecast(
        coord: Coord,
        units: String
    ): ResponseState<ResponseSingleWeatherForecast?> =
        withContext(Dispatchers.IO) {
            weatherRemoteData.getOneCallWeatherForecast(coord, units)
        }

    override suspend fun getWeatherForecastByCityName(cityName: String): ResponseState<List<NetworkWeatherForecast>?> =
        withContext(Dispatchers.IO) {
            weatherRemoteData.getWeatherForecastByCityName(cityName)
        }

    override suspend fun getWeatherForecastByLocation(
        coord: Coord,
        refresh: Boolean
    ): ResponseState<List<NetworkWeatherForecast>?> = withContext(Dispatchers.IO) {
        weatherRemoteData.getWeatherForecastByLocation(coord)
    }

    override suspend fun getWeatherFromDatabase(): WeatherAPIResponse? =
        withContext(Dispatchers.IO) {
            val mapperLocal = WeatherMapperLocal()
            weatherLocalData.getWeather()?.let {
                mapperLocal.transformToDomain(it)
            }
        }

    override suspend fun saveWeatherToDatabase(weather: WeatherAPIResponse?): Unit =
        withContext(Dispatchers.IO) {
            val mapper = WeatherMapperLocal()
            weather?.let { mapper.transformToDo(it) }?.let { weatherLocalData.saveWeather(it) }
        }


    override suspend fun deleteWeatherFromDatabase(weather: WeatherEntity?) {
        if (weather != null) {
            weatherLocalData.deleteWeather(weather)
        }
    }

    override suspend fun deleteAllWeatherFromDatabase() {
        weatherLocalData.deleteAllWeahter()
    }

    override suspend fun getAllWeatherFromDatabase(): List<WeatherEntity> {
        return weatherLocalData.getAllWeather()
    }

}
