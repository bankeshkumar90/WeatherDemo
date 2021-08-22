package com.app.weatherdemo.apputils

import android.content.Context
import android.content.SharedPreferences
import com.app.weatherdemo.appdata.model.Coord
import com.app.weatherdemo.apputils.Constants.BACKGROUND_IMAGE
import com.app.weatherdemo.apputils.Constants.CELSIUS
import com.app.weatherdemo.apputils.Constants.CITY_ID
import com.app.weatherdemo.apputils.Constants.CITY_LOCATION
import com.app.weatherdemo.apputils.Constants.NOT_FOUND
import com.app.weatherdemo.apputils.Constants.UNITS_TO_MEASURE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppSharedPrefrences @Inject constructor(@ApplicationContext context: Context) {

    private val sp: SharedPreferences by lazy {
        context.getSharedPreferences(Constants.SHARED_PREFRENCE, 0)
    }
    private var editor = sp.edit()

    fun clearSession() {
        editor.clear()
        editor.commit()
    }


    fun setBackgroundImage(path: String) {
        editor.putString(BACKGROUND_IMAGE, path)
        editor.commit()
    }

    fun getBrackgroundImage(): String? {
        return sp.getString(BACKGROUND_IMAGE, NOT_FOUND)
    }


    fun saveCityId(cityId: Int) {
        editor.putInt(CITY_ID, cityId)
        editor.commit()
    }

    fun getCityId() = sp.getInt(CITY_ID, 0)

    fun saveLocation(location: Coord) {
        val lat = location.lat
        val lon = location.lon
        val loc = "$lat,$lon"
        editor.putString(CITY_LOCATION, loc)

    }

    fun getLocation(): Coord {
        val loc = sp.getString(CITY_LOCATION, null)
        val arr = loc?.split(",")
        val lat = arr?.get(0)?.toDouble()
        val lon = arr?.get(1)?.toDouble()
        return Coord(lat, lon)
    }


    fun setUnitsOfMeasurement(uom: String) {
        editor.putString(UNITS_TO_MEASURE, uom)
        editor.commit()
    }

    fun getUnitsOfMeasurement(): String {
        return sp.getString(UNITS_TO_MEASURE, CELSIUS) ?: CELSIUS
    }

}