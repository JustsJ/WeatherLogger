package com.justsj.weatherapp.repository.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherDatabaseDao{

    @Insert
    fun insert(entity: CurrentWeather)

    @Update
    fun update(entity: CurrentWeather)

    @Query("SELECT * from weather_table WHERE id = :id")
    fun get(id: Long): CurrentWeather?

    @Query("SELECT * from weather_table WHERE location = :location")
    fun get(location: String): CurrentWeather?

    @Query("SELECT * from weather_table ORDER BY timestamp DESC")
    fun getAll(): LiveData<List<CurrentWeather>>

    @Query("DELETE from weather_table WHERE location = :location")
    fun delete(location: String)

}
