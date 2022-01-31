package com.justsj.weatherapp.weatheroverview

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justsj.weatherapp.repository.WeatherRepositoryFactory

class WeatherOverviewViewModelFactory(val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherOverviewViewModel::class.java)) {
            return WeatherOverviewViewModel(WeatherRepositoryFactory.repositoryInstance(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}