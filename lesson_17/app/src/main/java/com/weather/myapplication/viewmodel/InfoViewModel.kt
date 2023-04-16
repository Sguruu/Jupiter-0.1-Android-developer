package com.weather.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.myapplication.model.model.ResponseWeather
import com.weather.myapplication.model.repository.WeatherRepository
import okhttp3.Call

class InfoViewModel : ViewModel() {
    private val _weatherLiveData = MutableLiveData<ResponseWeather>()
    val responseWeatherLiveData
        get() = _weatherLiveData

    private val repository = WeatherRepository()

    private var currentCall: Call? = null

    fun requestWeather(lat: String, lon: String) {
        currentCall = repository.requestWeather(lat, lon) {
            it?.let {
                updateWeatherLiveData(it)
            }
        }
    }

    private fun updateWeatherLiveData(value: ResponseWeather) {
        _weatherLiveData.postValue(value)
        currentCall = null
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}
