package com.app.weatherdemo.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel @ViewModelInject constructor() : ViewModel(){
    lateinit var latLongLiveData:MutableLiveData<String>

    fun updateLiveData(lat:String){
        latLongLiveData.value = ""+ lat
    }
}