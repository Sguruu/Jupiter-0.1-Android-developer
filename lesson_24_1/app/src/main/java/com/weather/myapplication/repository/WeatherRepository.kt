package com.weather.myapplication.repository

import com.weather.myapplication.base.Result
import com.weather.myapplication.model.City
import com.weather.myapplication.model.Weather

interface WeatherRepository {
    suspend fun insertCites(cites: List<City>)
    suspend fun getAllCity(): List<City>
    fun createListCity(): List<City>
    suspend fun requestWeather(
        lat: String,
        lon: String
    ): Result<Weather>
    fun convertWeatherToJson(value: Weather): String
}