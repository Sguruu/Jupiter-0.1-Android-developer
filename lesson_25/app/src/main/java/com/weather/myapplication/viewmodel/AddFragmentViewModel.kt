package com.weather.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.myapplication.model.City
import com.weather.myapplication.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFragmentViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    init {
        Log.d("MyTest","init AddFragmentViewModel")
    }

    fun insertCites(cites: List<City>) {
        viewModelScope.launch {
            repository.insertCites(cites)
        }
    }
}
