package com.justsj.weatherapp.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "weather_table")
data class CurrentWeather(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    val location: String,

    val timestamp: Instant,

    val temperature: Double
)
