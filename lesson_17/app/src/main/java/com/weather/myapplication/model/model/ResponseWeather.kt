package com.weather.myapplication.model.model

data class ResponseWeather(
    val main: Main,
    val description: String
)

data class Main(
    val temp_min: Double,
    val temp_max: Double
)
