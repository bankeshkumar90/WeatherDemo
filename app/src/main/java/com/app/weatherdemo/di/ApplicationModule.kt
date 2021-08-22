package com.app.weatherdemo.di

import android.app.Application
import android.content.Context
import com.app.weatherdemo.apputils.MobiWeatherNetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun providesApplicationScoper() = CoroutineScope(SupervisorJob())

    @Singleton
    @Provides
    fun provideContext(application: Application):Context{
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesNetworkHelper(@ApplicationContext context: Context): MobiWeatherNetworkHelper {
        return MobiWeatherNetworkHelper(context)
    }
}