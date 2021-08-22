package com.app.weatherdemo.apputils

import com.app.weatherdemo.appdata.model.Coord
import com.app.weatherdemo.appdata.model.Weather
import com.app.weatherdemo.appdata.model.WeatherAPIResponse

object Constants {

    const val CITY_LONG = "LONG"
    const val QUERY = "appid"
    const val SERVER_API_KEY = "fae7190d7e6433ec3a45285ffcf55c86"
    const val UNITS = "units"
    const val MATRIC = "matric"
    const val APP_BASE_URL = "http://api.openweathermap.org/data/2.5/"
    const val SHARED_PREFRENCE = "APP_PREFS"
    const val UNITS_TO_MEASURE = "UNITS_TO_MEASURE"
    const val CELSIUS = "Celsius (°C)"
    const val FAHRENHEIT = "Fahrenheit (°F)"
    const val LOCATION_LONGITUDE = "LOCATION_LONGITUDE"
    const val LOCATION_LATITUDE = "LOCATION_LATITUDE"
    const val CHANNEL_ID = "CHANNEL_ID"
    const val CHANNEL_DESC = "CHANNEL_DESC"
    const val CHANNEL_NAME = "CHANNEL_NAME"

    const val BACKGROUND_IMAGE = "BACKGROUND_IMAGE"
    const val NOT_FOUND = "NOTFOUND"


    //table names
    const val TABLE_CITY = "city"
    const val CITY_ID = "CITY_ID"
    const val CITY_LOCATION="CITY_LOCATION"
    const val CITY_LAT="CITY_LAT"

    const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

    fun getWeatherResponse(lat:Double, long: Double, cityName:String, id:Int): WeatherAPIResponse{

        val coord = getCoord(lat, long)
        return WeatherAPIResponse(
            base = "stations",
            clouds = null,
            cod = 200,
            coord = coord,
            id = id,
            dt = 1629551312,
            name = cityName,
            sys = null,
            timezone = null,
            visibility = null,
            wind = null,
            main = null,
            weather = getWeatherList())
    }
    fun getCoord(lat:Double, long: Double):Coord{
        return Coord (lat, long)
    }
    fun getWeatherList():List<Weather>{
        val weather = Weather("Hazzy","ss",111,"dss")
        return  listOf(weather)
    }
    fun getArrayListOfRespWeather():ArrayList<WeatherAPIResponse>{
        val weatherList = getWeatherList()
        var ghbCoord = Coord(28.653933,77.445244)
        var banglore = Coord(12.972442,77.580643)
        var mumbai = Coord(19.076090,72.877426)
        var kolkata = Coord(22.572646,88.363895)
        var ahemdabad = Coord(23.033863,72.585022)

        /* val weatherDb = DBWeather(
             10001,
             cityName = "Ghaziabad",
             wind = Wind(29,55.22),
             weatherConditions = Main(42.21,1,22,21,2222,42.22,44.44,21.22),
             weatherDescription = weatherList,
             cityId = 10001
         )
         val listOfWeather = listOf(weatherDb)*/
        var arraylist: ArrayList<WeatherAPIResponse> = ArrayList()
        arraylist.add(
            WeatherAPIResponse(
                base = "stations",
                clouds = null,
                cod = 200,
                coord = ghbCoord,
                id = 10001,
                dt = 1629551312,
                name = "Ghaziabad",
                sys = null,
                timezone = null,
                visibility = null,
                wind = null,
                main = null,
                weather = weatherList
            )
        )
        arraylist.add(
            WeatherAPIResponse(
                base = "stations",
                clouds = null,
                cod = 200,
                coord = banglore,
                id = 10002,
                dt = 1629551312,
                name = "Bangalore",
                sys = null,
                timezone = null,
                visibility = null,
                wind = null,
                main = null,
                weather = weatherList
            )
        )
        arraylist.add(
            WeatherAPIResponse(
                base = "stations",
                clouds = null,
                cod = 200,
                coord = mumbai,
                id = 10003,
                dt = 1629551312,
                name = "Mumbai",
                sys = null,
                timezone = null,
                visibility = null,
                wind = null,
                main = null,
                weather = weatherList
            )
        )
        arraylist.add(
            WeatherAPIResponse(
                base = "stations",
                clouds = null,
                cod = 200,
                coord = kolkata,
                id = 10004,
                dt = 1629551312,
                name = "Kolkata",
                sys = null,
                timezone = null,
                visibility = null,
                wind = null,
                main = null,
                weather = weatherList
            )
        )
        arraylist.add(
            WeatherAPIResponse(
                base = "stations",
                clouds = null,
                cod = 200,
                coord = ahemdabad,
                id = 10005,
                dt = 1629551312,
                name = "Ahmedabad",
                sys = null,
                timezone = null,
                visibility = null,
                wind = null,
                main = null,
                weather = weatherList
            )
        )
        return arraylist
    }

}
