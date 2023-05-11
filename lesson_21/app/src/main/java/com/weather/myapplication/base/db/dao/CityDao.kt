package com.weather.myapplication.base.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.weather.myapplication.base.db.models.CityContract
import com.weather.myapplication.base.db.models.CityContract.Columns
import com.weather.myapplication.base.db.models.CityEntity

// аннотация позволяет Room понять что нужно генерировать для имплементации
@Dao
interface CityDao {
    /**
     * Получение всех городов
     */
    @Query("SELECT * FROM ${CityContract.TABLE_NAME}")
    suspend fun getAllCity(): List<CityEntity>

    /**
     * Метод добавление городов
     */
    @Insert()
    suspend fun insertCity(cites: List<CityEntity>)

    /**
     * Получение города по id
     */
    @Query("SELECT * FROM ${CityContract.TABLE_NAME} WHERE ${Columns.ID} = :${Columns.ID}")
    suspend fun getCityById(id: Long): CityEntity
}
