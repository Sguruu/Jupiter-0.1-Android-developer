package com.weather.myapplication.domain.repository

import com.weather.myapplication.domain.common.Result
import com.weather.myapplication.domain.model.City
import com.weather.myapplication.domain.model.Weather

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