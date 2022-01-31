package com.justsj.weatherapp.weatheroverview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justsj.WeatherLogger.R
import com.justsj.WeatherLogger.databinding.ListItemWeatherLocationBinding
import com.justsj.weatherapp.kelvinsToCelsius
import com.justsj.weatherapp.repository.database.CurrentWeather

class WeatherOverviewAdapter(val onEraseClicked: OnClickListener):
    ListAdapter<CurrentWeather, WeatherOverviewAdapter.CurrentWeatherViewHolder>(DiffCallback) {

    class CurrentWeatherViewHolder(private val binding: ListItemWeatherLocationBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(currentWeather: CurrentWeather) {
           binding.textLocation.text = currentWeather.location
           binding.textTemperature.text =
               binding.root.resources.getString(R.string.format_temperature, kelvinsToCelsius(currentWeather.temperature))
        }
        fun name() = binding.textLocation.text
    }

    class OnClickListener(val clickListener: (location: String) -> Unit) {
        fun onClick(location: String) = clickListener(location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentWeatherViewHolder {
        return CurrentWeatherViewHolder(ListItemWeatherLocationBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CurrentWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.findViewById<ImageView>(R.id.button_erase).setOnClickListener {
            onEraseClicked.onClick(holder.name().toString())
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<CurrentWeather>(){
        override fun areItemsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean {
            return oldItem.location == newItem.location
        }

        override fun areContentsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean {
            return oldItem.location == newItem.location
                    && oldItem.temperature == newItem.temperature
                    && oldItem.timestamp == newItem.timestamp
        }
    }
}