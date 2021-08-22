package com.app.weatherdemo.di

import com.app.weatherdemo.appdata.db.WeatherLocalDataImpl
import com.app.weatherdemo.appdata.db.WeatherRemoteDataImpl
import com.app.weatherdemo.appdata.db.interfaces.WeatherLocalData
import com.app.weatherdemo.appdata.db.interfaces.WeatherRemoteData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class DbSourceModule {

    @Binds
    abstract fun bindLocalWeatherData(weatherLocalDataImpl: WeatherLocalDataImpl):WeatherLocalData

    @Binds
    abstract fun bindAPIWeatherData(weatherRemoteDataImpl: WeatherRemoteDataImpl):WeatherRemoteData
}