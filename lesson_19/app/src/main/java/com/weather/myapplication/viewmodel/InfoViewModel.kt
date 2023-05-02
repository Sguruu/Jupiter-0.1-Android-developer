package com.weather.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.myapplication.base.Result
import com.weather.myapplication.model.model.ResponseWeather
import com.weather.myapplication.model.model.Weather
import com.weather.myapplication.model.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InfoViewModel : ViewModel() {
    private val _weatherLiveData = MutableLiveData<Weather>()
    val responseWeatherLiveData
        get() = _weatherLiveData

    private val repository = WeatherRepository()

    private var currentCall: retrofit2.Call<ResponseWeather>? = null

    // Dispatchers.Main - главный поток
    private val fragmentScope = CoroutineScope(Dispatchers.Main)

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

    fun startExampleCoroutines() {
        // использовать глобальный скоуп не рекомендуется, так как мы не можем влиять на него
        GlobalScope.launch {
            // тут можно написать код который будет работать в корутине
            // также запускать другие suspend функции
        }

        fragmentScope.launch {
            Log.d("MyTest", "текущий поток ${Thread.currentThread().name}")
        }

        Log.d("MyTest", "корутина была запущена ${Thread.currentThread().name}")
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
