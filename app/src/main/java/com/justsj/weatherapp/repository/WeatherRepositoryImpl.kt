package com.justsj.weatherapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.justsj.weatherapp.repository.database.CurrentWeather
import com.justsj.weatherapp.repository.database.WeatherDatabaseDao
import com.justsj.weatherapp.repository.network.CurrentWeatherDTO
import com.justsj.weatherapp.repository.network.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.Instant

class WeatherRepositoryImpl(
    private val weatherDao: WeatherDatabaseDao,
    private val weatherApi: WeatherApiService
) : WeatherRepository {

    override fun getAllCurrentWeather(): LiveData<List<CurrentWeather>> {
        return weatherDao.getAll()
    }

    override suspend fun retrieveCurrentWeather(location: String) {
        Log.i("Repository","retrieving ${location}")
        var weatherDTO: CurrentWeatherDTO? = null
        withContext(Dispatchers.IO) {
            try {
                weatherDTO = weatherApi.getCurrentWeather(location)
            }
            catch (e: Exception){
                Log.i("Repository","error ${location}")
                e.printStackTrace()
            }
        }
        Log.i("Repository","retrieved ${weatherDTO?.name}")
        //update existing entry for given location, or save a new one
        weatherDTO?.let {
            val existingCurrentWeather = weatherDao.get(weatherDTO!!.name)
            val retrievedCurrentWeather = CurrentWeather(
                location = weatherDTO!!.name,
                timestamp = Instant.now(),
                temperature = weatherDTO!!.main.temp)

            if (existingCurrentWeather != null){
                Log.i("Repository","updating...")
                retrievedCurrentWeather.id = existingCurrentWeather.id
                weatherDao.update(retrievedCurrentWeather)
            }
            else{
                Log.i("Repository","inserting...")
                weatherDao.insert(retrievedCurrentWeather)
            }
        }

    }

    override suspend fun removeLocation(location: String) {
        weatherDao.delete(location)
    }
}