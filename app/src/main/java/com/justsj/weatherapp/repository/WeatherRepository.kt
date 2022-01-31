package com.justsj.weatherapp.repository

import androidx.lifecycle.LiveData
import com.justsj.weatherapp.repository.database.CurrentWeather

interface WeatherRepository {
    fun getAllCurrentWeather(): LiveData<List<CurrentWeather>>
    suspend fun retrieveCurrentWeather(location: String)
    suspend fun removeLocation(location: String)
}