package com.weather.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.myapplication.base.Result
import com.weather.myapplication.model.model.ResponseWeather
import com.weather.myapplication.model.model.Weather
import com.weather.myapplication.model.repository.WeatherRepository
import kotlinx.coroutines.*
import java.math.BigInteger
import kotlin.random.Random
import kotlin.random.asJavaRandom

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

    private fun updateWeatherLiveData(value: Weather) {
        _weatherLiveData.postValue(value)
        currentCall = null
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }

    /* start test coroutines */
    fun startExampleCoroutines() {
        // использовать глобальный скоуп не рекомендуется, так как мы не можем влиять на него
        GlobalScope.launch {
            // тут можно написать код который будет работать в корутине
            // также запускать другие suspend функции
        }

        fragmentScope.launch {
            Log.d("MyTest", "текущий поток ${Thread.currentThread().name}")
            asyncExample()
        }
    }

    private suspend fun calculateNumber(): BigInteger {
        // чтобы превратить обычную функцию в suspend функцию можно использовать метод withContext
        // он позволяет изменить диспетчер для какого то блока кода
        return withContext(Dispatchers.Default) {
            Log.d("MyTest", "поток выполнения calculateNumber${Thread.currentThread().name}")
            /*
        Возвращает положительное значение BigInteger, которое, вероятно, является простым числом с указанной длиной бита. Вероятность того, что BigInteger, возвращаемый этим методом, является составным, не превышает 2-100.
        Параметры:
        bitLength — битовая длина возвращенного BigInteger.
        rnd — источник случайных битов, используемых для выбора кандидатов для проверки на простоту.
        Возвращает:
        BigInteger битов bitLength, который, вероятно, является простым
             */
            BigInteger.probablePrime(4000, Random.asJavaRandom())
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

    // async - запускает корутину и позволяет получить результат по окончании выполнения
    private fun asyncExample() {
        CoroutineScope(Dispatchers.IO).launch {
            val start = System.currentTimeMillis()
            // Deferred это объект который возвращает результат по окончании выполнения корутины
            val deferredResult1: Deferred<BigInteger> = async {
                calculateNumber()
            }
            val deferredResult2: Deferred<BigInteger> = async {
                calculateNumber()
            }

            val result1 = deferredResult1.await()
            val result2 = deferredResult1.await()

            // проверить работает ли корутина или нет
            // deferredResult.isActive
            // проверить выполнена ли корутина
            //  deferredResult.isCompleted
            // получение результата после завершения, может быть вызван только в suspend
            // deferredResult.await()
            val end = System.currentTimeMillis()
            Log.d("MyTest", "время запуска = ${end - start}")
        }
    }
    /* end test coroutines */
}
