package com.justsj.weatherapp.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.justsj.WeatherLogger.databinding.FragmentInputDialogBinding

class InputDialog(private val hint:String, val callback:(String) -> Unit): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentInputDialogBinding.inflate(inflater, container, false)

        binding.textInputDialog.hint = hint

        binding.buttonOk.setOnClickListener{
            callback(binding.buttonOk.text.toString())
            dismiss()
        }

        binding.buttonCancel.setOnClickListener{
            dismiss()
        }

        return binding.root
    }
}