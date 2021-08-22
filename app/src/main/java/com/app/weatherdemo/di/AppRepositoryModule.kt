package com.app.weatherdemo.di

import com.app.weatherdemo.appdata.repository.MobiWeatherRepository
import com.app.weatherdemo.appdata.repository.RepositoryModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindWeatherRepo(weatherRepository: MobiWeatherRepository): RepositoryModule
}