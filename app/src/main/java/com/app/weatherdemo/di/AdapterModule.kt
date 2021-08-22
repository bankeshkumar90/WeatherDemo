package com.app.weatherdemo.di

import com.app.weatherdemo.appdata.CityAdapter
import com.app.weatherdemo.appdata.adapters.MobiWeatherAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class AdapterModule {
    @Provides
    fun providesAdapterModule():MobiWeatherAdapter{
        return MobiWeatherAdapter()
    }
    /*@Provides
    fun providesCityAdapter(): CityAdapter {
        return CityAdapter()
    }*/
}