package com.weather.myapplication.base.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.myapplication.base.db.dao.CityDao
import com.weather.myapplication.base.db.models.CityEntity

// в аннотации указываем какие сущности будут храниться в бд, а также версию БД
@Database(entities = [CityEntity::class], version = WeatherDataBase.DB_VERSION)
abstract class WeatherDataBase : RoomDatabase() {

    // методы для возвращения DAO, имплментация создатся самим ROOM автоматически
    abstract fun CityDao(): CityDao

    companion object {
        const val DB_VERSION = 2
        const val DB_NAME = "Weather_DB"
    }
}
