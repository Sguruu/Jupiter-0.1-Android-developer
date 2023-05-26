package com.weather.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.myapplication.model.City
import com.weather.myapplication.repository.WeatherRepository
import kotlinx.coroutines.launch

class AddFragmentViewModel : ViewModel() {
    private val repository = WeatherRepository()

    fun insertCites(cites: List<City>) {
        viewModelScope.launch {
            repository.insertCites(cites)
        }
    }
}
