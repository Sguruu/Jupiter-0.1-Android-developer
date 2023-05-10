package com.weather.myapplication.base.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.weather.myapplication.base.db.models.CityContract
import com.weather.myapplication.base.db.models.CityEntity

// аннотация позволяет Room понять что нужно генерировать для имплементации
@Dao
interface CityDao {
    /**
     * Получение всех городов
     */
    @Query("SELECT * FROM ${CityContract.TABLE_NAME}")
    suspend fun getAllCity(): List<CityEntity>
}
