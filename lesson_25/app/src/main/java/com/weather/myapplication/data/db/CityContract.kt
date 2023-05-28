package com.weather.myapplication.data.db

object CityContract {
    const val TABLE_NAME = "WeatherEntity"

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val LAT = "lat"
        const val LON = "lon"
        const val PATH_IMAGE = "path_image"
    }
}
