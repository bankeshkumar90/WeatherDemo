package com.app.weatherdemo.apputils

import androidx.room.TypeConverter
import com.app.weatherdemo.appdata.model.Weather
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

class MobiWeatherTypeConverters {

    private val moshi = Moshi.Builder().build()
    private val myDataList : ParameterizedType =
        Types.newParameterizedType(List::class.java, Weather::class.java)

    private val weatherJSONAdapter:JsonAdapter<List<Weather>> = moshi.adapter(myDataList)

    @TypeConverter
    fun weatherDataToJSON(weatherData:List<Weather>) = weatherJSONAdapter.toJson(weatherData)

    @TypeConverter
    fun weatherStringToData(weatherString:String) = weatherJSONAdapter.fromJson(weatherString)
}