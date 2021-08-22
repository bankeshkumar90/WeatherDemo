package com.app.weatherdemo.di

import com.app.weatherdemo.appdata.api.MobiWeatherAPI
import com.app.weatherdemo.appdata.api.MyAPIRequestInterCeptor
import com.app.weatherdemo.apputils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RetrofitAPIModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(myAPIRequestInterCeptor: MyAPIRequestInterCeptor):OkHttpClient{
        return OkHttpClient.Builder().connectTimeout(30000L, TimeUnit.MILLISECONDS)
            .addInterceptor(myAPIRequestInterCeptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetroFitClient(okHttpClient: OkHttpClient): Retrofit.Builder{
        return Retrofit.Builder().baseUrl(Constants.APP_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun providesWeatherAPIService(retrofit: Retrofit.Builder):MobiWeatherAPI{
        return retrofit.build().create(MobiWeatherAPI::class.java)
    }
}