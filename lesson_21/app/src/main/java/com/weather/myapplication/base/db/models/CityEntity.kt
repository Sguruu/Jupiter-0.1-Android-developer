package com.weather.myapplication.base.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Указание названия таблицы
@Entity(
    tableName = CityContract.TABLE_NAME
    // пример создания нескольких первичных ключей
    // primaryKeys = [CityContract.Columns.ID, CityContract.Columns.NAME]
)
data class CityEntity(
    // указание первичного ключа через аннотацию @PrimaryKey
    @PrimaryKey
    // указать имя в колонке
    @ColumnInfo(CityContract.Columns.ID)
    val id: Long,
    @ColumnInfo(CityContract.Columns.NAME)
    val name: String,
    @ColumnInfo(CityContract.Columns.TEMP_MIN)
    val tempMin: Double,
    @ColumnInfo(CityContract.Columns.TEMP_MAX)
    val tempMax: Double,
    @ColumnInfo(CityContract.Columns.PATH_IMAGE)
    val pathImage: String? = null
)
