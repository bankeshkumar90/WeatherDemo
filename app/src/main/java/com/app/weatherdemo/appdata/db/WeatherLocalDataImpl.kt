package com.app.weatherdemo.appdata.db

import com.app.weatherdemo.appdata.db.interfaces.MobiWeatherDao
import com.app.weatherdemo.appdata.db.interfaces.WeatherLocalData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherLocalDataImpl @Inject constructor(private val dao: MobiWeatherDao): WeatherLocalData {
    override suspend fun getWeather(): WeatherEntity? = withContext(Dispatchers.IO){
        return@withContext dao.getWeather()
    }

    override suspend fun saveWeather(weather: WeatherEntity) = withContext(Dispatchers.IO){
         dao.insertWeather(weather)
    }

    override suspend fun deleteWeather(weather: WeatherEntity) = withContext(Dispatchers.IO){
        dao.deleteWeather(weather)
    }

    override suspend fun deleteAllWeahter() = withContext(Dispatchers.IO){
        dao.deleteAllWeather()
    }

    override suspend fun getAllWeather(): List<WeatherEntity> = withContext(Dispatchers.IO) {
        dao.getAllWeather()
    }

    /*override suspend fun deleteWeatherByCityName(city_name:String)  = withContext(Dispatchers.IO){
        dao.deleteWeatherByCityName(city_name)
     }*/

}