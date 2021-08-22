package com.app.weatherdemo.appdata.api

import com.app.weatherdemo.apputils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyAPIRequestInterCeptor @Inject constructor() : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val appUrl = chain.request().url.newBuilder()
            .addQueryParameter(Constants.QUERY,Constants.SERVER_API_KEY)
            .addQueryParameter(Constants.UNITS,Constants.MATRIC)
            .build()
        val apiRequest = chain.request().newBuilder().url(appUrl).build()
        return chain.proceed(apiRequest)
    }

}