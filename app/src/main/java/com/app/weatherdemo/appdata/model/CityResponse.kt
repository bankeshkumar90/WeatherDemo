package com.app.weatherdemo.appdata.model

import com.squareup.moshi.Json

class CityResponse (
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "coord")
    val coord: Coord?,
    @Json(name = "sunrise")
    val sunrise: Long,
    @Json(name = "sunset")
    val sunset: Long, )