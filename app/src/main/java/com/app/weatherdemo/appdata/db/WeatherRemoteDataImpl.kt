package com.app.weatherdemo.appdata.db

import com.app.weatherdemo.appdata.api.MobiWeatherAPI
import com.app.weatherdemo.appdata.api.ResponseState
import com.app.weatherdemo.appdata.db.interfaces.WeatherRemoteData
import com.app.weatherdemo.appdata.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRemoteDataImpl @Inject constructor(private val api: MobiWeatherAPI):WeatherRemoteData {

    override suspend fun getWeatherByLocation(coord: Coord): ResponseState<WeatherAPIResponse?> =
        withContext(Dispatchers.IO){
            return@withContext try {
                val weatherAPIResult = api.getWeatherByLocation(coord.lat.toString(), coord.lon.toString())
                if(weatherAPIResult.isSuccessful){
                    ResponseState.Success(weatherAPIResult.body())
                }else{
                    ResponseState.Success(null)
                }
            }catch (e:Exception){
                if (e.message != null)
                    ResponseState.Error(e.message!!)
                else {
                    ResponseState.Error("Please Re-Try!")
                }
            }
        }


    override suspend fun getWeatherByZipAndCountry(zipAndCountry: ZipCountryCode): ResponseState<WeatherAPIResponse?> =
        withContext(Dispatchers.IO){
            return@withContext try {
                val weatherAPIResponse = api.getWeatherByZipAndContryCode(zipAndCountry.toString())
                if(weatherAPIResponse.isSuccessful){
                    ResponseState.Success(weatherAPIResponse.body())
                }else{
                    ResponseState.Success(null)
                }
            }catch (e:Exception){
                if (e.message != null)
                    ResponseState.Error(e.message!!)
                else {
                    ResponseState.Error("Please Re-Try!")
                }
            }
    }

    override suspend fun getWeatherByCityName(cityName: String): ResponseState<WeatherAPIResponse?> =
        withContext(Dispatchers.IO){
            return@withContext try {
                val weatherAPIResponse = api.getWeatherByCityName(cityName)
                if(weatherAPIResponse.isSuccessful){
                    ResponseState.Success(weatherAPIResponse.body())
                }else{
                    ResponseState.Success(null)
                }
            }catch (e:Exception){
                if (e.message != null)
                    ResponseState.Error(e.message!!)
                else {
                    ResponseState.Error("Some error occured!")
                }
            }

    }

    override suspend fun getWeatherByCityId(cityId: Int): ResponseState<WeatherAPIResponse?> =
        withContext(Dispatchers.IO){
            return@withContext try {
                val weatherAPIResponse = api.getWeatherByCityID(""+cityId)
                if(weatherAPIResponse.isSuccessful){
                    ResponseState.Success(weatherAPIResponse.body())
                }else{
                    ResponseState.Success(null)
                }
            }catch (e:Exception){
                if (e.message != null)
                    ResponseState.Error(e.message!!)
                else {
                    ResponseState.Error("Some error occured!")
                }
            }
    }

    override suspend fun getOneCallWeatherForecast(
        coord: Coord,
        units: String
    ): ResponseState<ResponseSingleWeatherForecast?> =
        withContext(Dispatchers.IO){
            return@withContext try {
                val weatherAPIResponse = api.getWeatherForecast(coord.lat.toString() ,coord.lon.toString(), units)
                if(weatherAPIResponse.isSuccessful){
                    ResponseState.Success(weatherAPIResponse.body())
                }else{
                    ResponseState.Success(null)
                }
            }catch (e:Exception){
                if (e.message != null)
                    ResponseState.Error(e.message!!)
                else {
                    ResponseState.Error("Some error occured!")
                }
            }
    }

    override suspend fun getWeatherForecastByCityName(cityName: String): ResponseState<List<NetworkWeatherForecast>?> =
        withContext(Dispatchers.IO){
            return@withContext try {
                val weatherAPIResponse = api.getWeatherForecastByCityName(cityName)
                if(weatherAPIResponse.isSuccessful){
                    ResponseState.Success(weatherAPIResponse.body()!!.list)
                }else{
                    ResponseState.Success(null)
                }
            }catch (e:Exception){
                if (e.message != null)
                    ResponseState.Error(e.message!!)
                else {
                    ResponseState.Error("Some error occured!")
                }
            }
    }

    override suspend fun getWeatherForecastByLocation(coord: Coord): ResponseState<List<NetworkWeatherForecast>?>
            = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = api.getWeatherForecastByLocation(coord.lat.toString(), coord.lon.toString())
            if (result.isSuccessful) {
                ResponseState.Success(result.body()?.list)
            } else {
                ResponseState.Success(null)
            }
        } catch (e: Exception) {
            if (e.message != null)
                ResponseState.Error(e.message!!)
            else {
                ResponseState.Error("Some error occured!")
            }
        }
    }

}