package com.weather.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.myapplication.model.City
import com.weather.myapplication.repository.WeatherRepository
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    private val repository = WeatherRepository()
    private val _cityListLiveData = MutableLiveData<List<City>>()
    val cityListLiveData
        get() = _cityListLiveData

    // вызывается при инициализации ViewModel
    init {
        getAllCity()
    }

    private fun getAllCity() {
        viewModelScope.launch {
            val listCity = repository.createListCity() + repository.getAllCity()
            updateCityListLiveData(listCity)
        }
    }

    private fun updateCityListLiveData(value: List<City>) {
        _cityListLiveData.value = value
    }
}
