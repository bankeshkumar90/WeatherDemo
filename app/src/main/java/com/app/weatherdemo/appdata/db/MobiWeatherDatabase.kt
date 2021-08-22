package com.app.weatherdemo.appdata.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.weatherdemo.appdata.db.interfaces.MobiWeatherDao
import com.app.weatherdemo.apputils.MobiWeatherTypeConverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(MobiWeatherTypeConverters::class)
abstract class MobiWeatherDatabase:RoomDatabase() {
    abstract fun mobiWeatherDao(): MobiWeatherDao

    companion object{
        val dbName = "MobiWeather"
    }

    class DBCallback @Inject constructor(
        private val database: Provider<MobiWeatherDatabase>,
        private val appScope:CoroutineScope
    ) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val appDao = database.get().mobiWeatherDao()
            appScope.launch {

            }
        }
    }

 }