package com.justsj.weatherapp.repository.network

import com.squareup.moshi.Json


//{
//    "coord": {
//    "lon": -0.13,
//    "lat": 51.51
//},
//    "weather": [
//    {
//        "id": 300,
//        "main": "Drizzle",
//        "description": "light intensity drizzle",
//        "icon": "09d"
//    }
//    ],
//    "base": "stations",
//    "main": {
//    "temp": 280.32,
//    "pressure": 1012,
//    "humidity": 81,
//    "temp_min": 279.15,
//    "temp_max": 281.15
//},


data class CurrentWeatherDTO(
    val id: Long,
    val name: String,
    val main: MainData,
    @Json(name = "weather") val descriptions: List<WeatherDescriptor>)

data class MainData(
    val temp: Double,
    val pressure: Double,
    val humidity: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double
)

data class WeatherDescriptor(
    val id: Long,
    @Json(name = "main") val title: String,
    val description: String,
    val icon: String
)
