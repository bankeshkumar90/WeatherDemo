package com.app.weatherdemo.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weatherdemo.appdata.db.WeatherEntity
import com.app.weatherdemo.appdata.model.WeatherAPIResponse
import com.app.weatherdemo.appdata.repository.MobiWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor(val repository: MobiWeatherRepository) : ViewModel(){
    private val _citiList : MutableLiveData<WeatherAPIResponse?> = MutableLiveData()
    val cityList : MutableLiveData<List<WeatherEntity?>> = MutableLiveData()

    /*****
     * request saved cities from database
     */
    fun getAllCitiesFromDb() = viewModelScope.launch{
        getAllCitiesSafely()
    }
    private suspend fun getAllCitiesSafely(){
        delay(500)
        withContext(Dispatchers.IO){
            val cities = repository.getAllWeatherFromDatabase()
            cityList.postValue(cities)
        }
    }

    /***
     * saving some city to show on Start
     */
    fun saveCityDeatilsInDb(weatherAPIResponse: WeatherAPIResponse){
        viewModelScope.launch {
            saveCityDetailsSafely(weatherAPIResponse)
        }
    }
    private suspend fun saveCityDetailsSafely(weatherAPIResponse: WeatherAPIResponse){
       delay(500)
        withContext(Dispatchers.IO){
            repository.saveWeatherToDatabase(weatherAPIResponse)
        }
    }

    /***
     * saving some city to show on Start
     */
    fun deleteCityFromDb(weatherAPIResponse: WeatherEntity){
        viewModelScope.launch {
            repository.deleteWeatherFromDatabase(weatherAPIResponse)
        }

    }

    private suspend fun deleteCityDetailsSafely(weatherAPIResponse: WeatherEntity){
        delay(200)
        withContext(Dispatchers.IO){
            repository.deleteWeatherFromDatabase(weatherAPIResponse)
        }
        getAllCitiesFromDb()
    }
}