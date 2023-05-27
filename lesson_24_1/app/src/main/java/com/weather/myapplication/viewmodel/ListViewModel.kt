package com.weather.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.myapplication.model.City
import com.weather.myapplication.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _cityListLiveData = MutableLiveData<List<City>>()
    val cityListLiveData
        get() = _cityListLiveData

    fun getAllCity() {
        viewModelScope.launch {
            val listCity = repository.createListCity() + repository.getAllCity()
            updateCityListLiveData(listCity)
        }
    }

    private fun updateCityListLiveData(value: List<City>) {
        _cityListLiveData.value = value
    }
}
