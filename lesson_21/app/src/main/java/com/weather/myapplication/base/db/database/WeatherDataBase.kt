package com.weather.myapplication.base.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.myapplication.base.db.dao.CityDao

// в аннотации указываем какие сущности будут храниться в бд, а также версию БД
@Database(entities = [], version = WeatherDataBase.DB_VERSION)
abstract class WeatherDataBase : RoomDatabase() {

    // методы для возвращения DAO, имплментация создатся самим ROOM автоматически
    abstract fun CityDao(): CityDao

    companion object {
        const val DB_VERSION = 1
    }
}
