package com.app.weatherdemo.appdata.db.interfaces

import androidx.room.*
import com.app.weatherdemo.appdata.db.WeatherEntity

@Dao
interface MobiWeatherDao {

    // convert all returning to livedata

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(vararg  weatherEntity: WeatherEntity)

    @Query("SELECT * FROM mobi_weatherTable ORDER BY unique_id DESC LIMIT 1")
    suspend fun getWeather(): WeatherEntity

    @Query("SELECT * FROM mobi_weatherTable ORDER BY unique_id DESC")
    suspend fun getAllWeather(): List<WeatherEntity>

    @Query("DELETE FROM mobi_weatherTable")
    suspend fun deleteAllWeather()

    /*@Query("DELETE FROM mobi_weatherTable WHERE city_name = city_name")
    suspend fun deleteWeatherByCityName(city_name:String)*/

    @Delete
    suspend fun deleteWeather(weather: WeatherEntity)

}