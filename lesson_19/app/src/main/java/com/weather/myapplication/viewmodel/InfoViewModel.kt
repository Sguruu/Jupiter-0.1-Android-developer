package com.weather.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.myapplication.base.Result
import com.weather.myapplication.model.model.ResponseWeather
import com.weather.myapplication.model.model.Weather
import com.weather.myapplication.model.repository.WeatherRepository
import kotlinx.coroutines.*

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

//        fragmentScope.launch {
//            while (true) {
//                // задержка корутины на 1 сек
//                delay(1000)
//                Log.d("MyTest", "текущий поток 2 ${Thread.currentThread().name}")
//            }
//        }

        Log.d("MyTest", "корутина была запущена ${Thread.currentThread().name}")

        exampleFlowSwitching()
    }

    suspend fun calculateNumber(): Int {
        // чтобы превратить обычную функцию в suspend функцию можно использовать метод withContext
        // он позволяет изменить диспетчер для какого то блока кода
        return withContext(Dispatchers.Default) {
            Log.d("MyTest", "поток выполнения calculateNumber${Thread.currentThread().name}")
            5
        }
    }

    // демонстрация переключения потока
    private fun exampleFlowSwitching() {
        CoroutineScope(Dispatchers.IO).launch {
            (0..200).forEach {
                Log.d("MyTest", "Старт выполнения потока ${Thread.currentThread().name}")
                delay(100)
                Log.d("MyTest", "Конец выполнения потока ${Thread.currentThread().name}")
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
