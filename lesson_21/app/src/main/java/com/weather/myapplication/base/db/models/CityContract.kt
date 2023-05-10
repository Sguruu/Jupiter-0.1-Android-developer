package com.weather.myapplication.base.db.models

object CityContract {
    const val TABLE_NAME = "WeatherEntity"

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val TEMP_MIN = "temp_min"
        const val TEMP_MAX = "temp_max"
        const val PATH_IMAGE = "path_image"
    }
}
