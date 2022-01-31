package com.justsj.weatherapp.repository

import android.content.Context
import com.justsj.weatherapp.repository.database.WeatherDatabase
import com.justsj.weatherapp.repository.network.WeatherApi

class WeatherRepositoryFactory {

    companion object{
        private var instance: WeatherRepository? = null

        fun repositoryInstance(context: Context): WeatherRepository{
            if (instance == null) {
                instance = WeatherRepositoryImpl(
                    WeatherDatabase.getInstance(context).weatherDatabaseDao,
                    WeatherApi.create()
                )
            }
            return instance!!
        }
    }
}