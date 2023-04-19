package com.weather.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.myapplication.model.model.Weather
import com.weather.myapplication.model.repository.WeatherRepository
import okhttp3.Call

class InfoViewModel : ViewModel() {
    private val _weatherLiveData = MutableLiveData<Weather>()
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

    private fun updateWeatherLiveData(value: Weather) {
        _weatherLiveData.postValue(value)
        currentCall = null
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}
