package com.weather.myapplication.data.mapper

import com.weather.myapplication.data.models.network.RequestWeather
import com.weather.myapplication.data.models.network.ResponseWeather
import com.weather.myapplication.domain.model.Weather

class MapperWeather {
    fun mapToWeather(weather: ResponseWeather): Weather {
        return Weather(
            weather.main.tempMin,
            weather.main.tempMax,
            weather.weatherCurrent[0].description
        )
    }

    fun mapToRequestWeather(weather: Weather): RequestWeather {
        return RequestWeather(
            weather.tempMin,
            weather.tempMax,
            weather.descriptionWeather
        )
    }
}
