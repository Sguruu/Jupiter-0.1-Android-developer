package com.weather.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.myapplication.model.model.City
import com.weather.myapplication.model.repository.WeatherRepository

class ListViewModel : ViewModel() {
    private val repository = WeatherRepository()
    private val _cityListLiveData = MutableLiveData<List<City>>(repository.createListCity())
    val cityListLiveData
        get() = _cityListLiveData
}
