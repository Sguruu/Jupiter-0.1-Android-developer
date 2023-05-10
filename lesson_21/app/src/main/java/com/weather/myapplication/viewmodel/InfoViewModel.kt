package com.weather.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.myapplication.base.Result
import com.weather.myapplication.base.network.model.ResponseWeather
import com.weather.myapplication.model.Weather
import com.weather.myapplication.repository.WeatherRepository
import kotlinx.coroutines.*

class InfoViewModel : ViewModel() {
    private val _weatherLiveData = MutableLiveData<Weather>()
    val responseWeatherLiveData
        get() = _weatherLiveData

    private val repository = WeatherRepository()

    private var currentCall: retrofit2.Call<ResponseWeather>? = null

    fun requestWeather(lat: String, lon: String) {
        viewModelScope.launch {
            repository.requestWeather(lat, lon).apply {
                when (this) {
                    is Result.Success<Weather> -> {
                        updateWeatherLiveData(this.data)
                    }
                    is Result.Error -> {
                        // тут обрабатываем ошибку
                        Log.d("MyTest", "Обработка ошибки∆")
                    }
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
