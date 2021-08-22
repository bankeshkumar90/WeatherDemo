package com.app.weatherdemo.appdata.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.weatherdemo.appdata.model.Coord
import com.app.weatherdemo.appdata.model.Main
import com.app.weatherdemo.appdata.model.Weather
import com.app.weatherdemo.appdata.model.Wind

@Entity(tableName = "mobi_weatherTable")
data class WeatherEntity (
    @ColumnInfo(name = "unique_id")
    @PrimaryKey(autoGenerate = true)
    val uId:Int = 0,
    @ColumnInfo(name = "city_id")
    val cityId:Int,
    @ColumnInfo(name="city_name")
    val cityName:String?,
    @Embedded
    val wind: Wind?,
    @ColumnInfo(name="weather_details")
    val weatherDescription:List<Weather>,
    @Embedded
    val weatherConditions: Main?,
    @Embedded
    val coord: Coord?)