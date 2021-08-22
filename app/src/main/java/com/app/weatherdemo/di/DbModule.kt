package com.app.weatherdemo.di

import android.content.Context
import androidx.room.Room
import com.app.weatherdemo.appdata.db.interfaces.MobiWeatherDao
import com.app.weatherdemo.appdata.db.MobiWeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun providesMobiWeatherDataBase(@ApplicationContext context: Context): MobiWeatherDatabase{
        return Room.databaseBuilder(context,
        MobiWeatherDatabase::class.java,
        MobiWeatherDatabase.dbName).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesDataOfDao(db: MobiWeatherDatabase): MobiWeatherDao {
        return db.mobiWeatherDao()
    }

}