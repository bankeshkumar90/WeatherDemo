package com.app.weatherdemo.apputils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MobiWeatherNetworkHelper @Inject constructor(@ApplicationContext val context: Context){

    fun isNetworkConnected():Boolean{
        var connected = false
        var cManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){

        }else{

        }
        return connected
    }
}