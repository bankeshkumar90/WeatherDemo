package com.app.weatherdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp //This annotation will generate base class that cares injecting members into android class
class WeatherApplication :Application(){
    @Override
    override fun onCreate() {
        super.onCreate()
    }
}