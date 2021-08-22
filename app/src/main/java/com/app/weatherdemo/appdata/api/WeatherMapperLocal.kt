package com.weather.weatherify.mappers

import com.app.weatherdemo.appdata.db.WeatherEntity
import com.app.weatherdemo.appdata.api.BaseMapper
import com.app.weatherdemo.appdata.model.WeatherAPIResponse

class WeatherMapperLocal : BaseMapper<WeatherEntity, WeatherAPIResponse> {
    override fun transformToDomain(type: WeatherEntity): WeatherAPIResponse =
        WeatherAPIResponse(
            base = null,
            clouds = null,
            cod = null,
            coord = null,
            id = type.cityId,
            dt = null,
            name = type.cityName,
            sys = null,
            timezone = null,
            visibility = null,
            wind = type.wind,
            main = type.weatherConditions,
            weather = type.weatherDescription
        )

    override fun transformToDo(type: WeatherAPIResponse): WeatherEntity =
        WeatherEntity(
            uId = type.id,
            cityName = type.name,
            wind = type.wind,
            weatherConditions = type.main,
            weatherDescription = type.weather,
            cityId = type.id,
            coord = type.coord
        )
}