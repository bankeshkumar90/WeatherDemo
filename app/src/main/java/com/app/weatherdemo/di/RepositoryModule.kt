package com.app.weatherdemo.di

import com.app.weatherdemo.appdata.db.interfaces.WeatherLocalData
import com.app.weatherdemo.appdata.db.interfaces.WeatherRemoteData
import com.app.weatherdemo.appdata.repository.MobiWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherLocalData: WeatherLocalData, weatherRemoteData: WeatherRemoteData):MobiWeatherRepository{
        return MobiWeatherRepository(weatherRemoteData, weatherLocalData)
    }
}