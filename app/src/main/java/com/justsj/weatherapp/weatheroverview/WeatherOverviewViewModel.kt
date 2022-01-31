package com.justsj.weatherapp.weatheroverview

import android.app.Application
import androidx.lifecycle.*
import com.justsj.weatherapp.repository.WeatherRepository

import com.justsj.weatherapp.repository.WeatherRepositoryFactory
import com.justsj.weatherapp.repository.database.CurrentWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherOverviewViewModel(val repository: WeatherRepository)
    : ViewModel() {

    val weather: LiveData<List<CurrentWeather>>
        get() = repository.getAllCurrentWeather()

    private val _refreshingWeatherData = MutableLiveData<Boolean>()
    val refreshingWeatherData: LiveData<Boolean>
        get() = _refreshingWeatherData

    private val _showNewLocationInput = MutableLiveData<Boolean>()
    val showNewLocationInput: LiveData<Boolean>
        get() = _showNewLocationInput


    fun addNewLocation(location: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.retrieveCurrentWeather(location)
            }
        }
    }

    fun removeLocation(location: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.removeLocation(location)
            }
        }
    }

    fun showNewLocationInputComplete(){
        _showNewLocationInput.value = false
    }

    fun floatingButtonPressed(){
        _showNewLocationInput.value = true
    }
}