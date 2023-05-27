package com.weather.myapplication.data.db.dao

import androidx.room.*
import com.weather.myapplication.data.db.CityContract
import com.weather.myapplication.data.db.CityContract.Columns
import com.weather.myapplication.data.models.db.CityEntity

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
    @Query("SELECT * FROM ${CityContract.TABLE_NAME} WHERE ${Columns.ID} = :id")
    suspend fun getCityById(id: Long): CityEntity

    /**
     * Удаление пользователя
     */
    @Delete
    suspend fun delete(cityEntity: CityEntity)

    /**
     * Удаление пользователя по id
     */
    @Query("DELETE FROM ${CityContract.TABLE_NAME} WHERE ${Columns.ID} = :cityID ")
    suspend fun deleteCityById(cityID: Long)

    /**
     * обновление пользователя
     */
    @Update
    suspend fun updateCity(city: CityEntity)
}
