package com.weather.myapplication.data.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weather.myapplication.data.db.CityContract

// Указание названия таблицы
@Entity(
    tableName = CityContract.TABLE_NAME
    // пример создания нескольких первичных ключей
    // primaryKeys = [CityContract.Columns.ID, CityContract.Columns.NAME]
)
data class CityEntity(
    // указание первичного ключа через аннотацию @PrimaryKey
    @PrimaryKey(autoGenerate = true)
    // указать имя в колонке
    @ColumnInfo(CityContract.Columns.ID)
    val id: Long = 0,
    @ColumnInfo(CityContract.Columns.NAME)
    val name: String,
    @ColumnInfo(CityContract.Columns.LAT)
    val lat: Double,
    @ColumnInfo(CityContract.Columns.LON)
    val lon: Double,
    @ColumnInfo(CityContract.Columns.PATH_IMAGE)
    val pathImage: String? = null
)
