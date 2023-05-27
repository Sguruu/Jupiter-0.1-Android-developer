package com.weather.myapplication.data.mapper

import com.weather.myapplication.data.models.db.CityEntity
import com.weather.myapplication.domain.model.City
import javax.inject.Inject

class MapperCity {
    fun mapToCityEntity(city: City): CityEntity {
        return CityEntity(
            name = city.name,
            lat = city.lat.toDouble(),
            lon = city.lon.toDouble()
        )
    }

    fun mapToCity(cityEntity: CityEntity): City {
        return City(
            name = cityEntity.name,
            lat = cityEntity.lat.toString(),
            lon = cityEntity.lon.toString()
        )
    }
}
