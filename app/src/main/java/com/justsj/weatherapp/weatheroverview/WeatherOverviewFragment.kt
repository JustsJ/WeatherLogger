package com.justsj.weatherapp.weatheroverview

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.justsj.WeatherLogger.R
import com.justsj.WeatherLogger.databinding.FragmentWeatherOverviewBinding

class WeatherOverviewFragment: Fragment() {


    private val viewModel: WeatherOverviewViewModel by lazy {
        ViewModelProvider(this,WeatherOverviewViewModelFactory(requireContext()))[WeatherOverviewViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentWeatherOverviewBinding.inflate(inflater)


        val adapter = WeatherOverviewAdapter(
            WeatherOverviewAdapter.OnClickListener {
                viewModel.removeLocation(it)
            })
        binding.recyclerViewWeather.adapter = adapter

        viewModel.weather.observe(viewLifecycleOwner,{
            Log.i("Fragment","Weather Updated! ${it.size} ${adapter.itemCount}")
            adapter.submitList(it)

        })

        viewModel.showNewLocationInput.observe(viewLifecycleOwner,{
            if (it){
                showInputDialog()
                viewModel.showNewLocationInputComplete()
            }
        })

        binding.floatingActionButton.setOnClickListener{
            viewModel.floatingButtonPressed()
        }

        return binding.root
    }

    private fun showInputDialog(){
        val editText = EditText(requireContext())

        AlertDialog.Builder(requireContext())
            .setMessage(R.string.input_enter_location)
            .setView(editText)
            .setPositiveButton(R.string.button_ok) { dialogInterface, _ ->
                Log.i("AlertDialog","adding new location: ${editText.text.toString()}")
                viewModel.addNewLocation(editText.text.toString())
                dialogInterface.dismiss()
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
            .show()
    }
}