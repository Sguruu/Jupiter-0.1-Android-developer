package com.weather.myapplication.domain.model

data class City(
    val name: String,
    val imageLink: String? = null,
    val lat: String,
    val lon: String
)
