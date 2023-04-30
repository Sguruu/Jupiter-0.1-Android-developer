package com.weather.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.myapplication.base.Result
import com.weather.myapplication.model.model.ResponseWeather
import com.weather.myapplication.model.model.Weather
import com.weather.myapplication.model.repository.WeatherRepository

class InfoViewModel : ViewModel() {
    private val _weatherLiveData = MutableLiveData<Weather>()
    val responseWeatherLiveData
        get() = _weatherLiveData

    private val repository = WeatherRepository()

    private var currentCall: retrofit2.Call<ResponseWeather>? = null

    fun requestWeather(lat: String, lon: String) {
        currentCall = repository.requestWeather(lat, lon) {
            when (it) {
                is Result.Success<Weather> -> {
                    updateWeatherLiveData(it.data)
                }
                is Result.Error -> {
                    // тут обрабатываем ошибку
                }
            }
        }
    }

    fun convertWeatherToJson(value: Weather): String {
        return repository.convertWeatherToJson(value)
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
