package com.app.weatherdemo.ui.weatherdetails

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weatherdemo.appdata.api.ResponseState
import com.app.weatherdemo.appdata.model.Coord
import com.app.weatherdemo.appdata.model.NetworkWeatherForecast
import com.app.weatherdemo.appdata.model.WeatherAPIResponse
import com.app.weatherdemo.appdata.repository.MobiWeatherRepository
import com.app.weatherdemo.apputils.NetworkConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetailsViewModel  @ViewModelInject constructor(val repository: MobiWeatherRepository, val networkConnection: NetworkConnection) : ViewModel(){

    //get weather details by coordinates
    private val _weatherData: MutableLiveData<ResponseState<WeatherAPIResponse?>> = MutableLiveData()
    val weatherData: LiveData<ResponseState<WeatherAPIResponse?>> get() = _weatherData

    fun getWeatherByLocation(coord: Coord) = viewModelScope.launch {
        safeGetWeatherByLocation(coord)
    }

    private suspend fun safeGetWeatherByLocation(coord: Coord) {

        withContext(Dispatchers.IO){
            _weatherData.postValue(ResponseState.Loading())
            delay(500L)
            if(networkConnection.isNetworkConnected()){
                try {
                    val response = repository.getWeatherByLocation(coord = coord, true)
                    _weatherData.postValue(response)
                    repository.saveWeatherToDatabase(response.data)
                }catch(e:Exception){
                }
            }
            else{
                _weatherData.postValue(ResponseState.Error("No internet Connection"))
            }
        }
    }


    /******
     * Forecast for 5 days
     */
    private val _weatherForecast: MutableLiveData<ResponseState<List<NetworkWeatherForecast>?>> = MutableLiveData()
    val weatherForecast: LiveData<ResponseState<List<NetworkWeatherForecast>?>> get() = _weatherForecast


    fun getWeatherForecastByLocation(coord: Coord) = viewModelScope.launch {
        safeGetWeatherForecastByLocation(coord)
    }

    private  suspend fun safeGetWeatherForecastByLocation(coord: Coord) {
        withContext(Dispatchers.IO){
            _weatherForecast.postValue(ResponseState.Loading())
            if(networkConnection.isNetworkConnected()){
                val response = repository.getWeatherForecastByLocation(coord=coord, true)
                _weatherForecast.postValue(response)
            }
            else{
                _weatherForecast.postValue(ResponseState.Error("No internet Connection"))
            }
        }

    }


    @SuppressLint("SimpleDateFormat")
    fun currentSystemTime(): String {
        val currentTime = System.currentTimeMillis()
        val date = Date(currentTime)
        val dateFormat = SimpleDateFormat("EEEE MMM d, hh:mm aaa")
        return dateFormat.format(date)
    }

}