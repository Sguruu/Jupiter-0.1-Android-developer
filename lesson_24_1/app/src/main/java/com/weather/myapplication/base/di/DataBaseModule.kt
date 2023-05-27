package com.weather.myapplication.base.di

import android.app.Application
import androidx.room.Room
import com.weather.myapplication.base.db.dao.CityDao
import com.weather.myapplication.base.db.database.WeatherDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
// указание к какому компоненту пренадлежит данный модуль
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun provideDataBase(context: Application): WeatherDataBase {
        return Room.databaseBuilder(
            context,
            WeatherDataBase::class.java,
            WeatherDataBase.DB_NAME
        ).build()
    }

    @Provides
    fun provideUserDao(db: WeatherDataBase): CityDao {
        return db.CityDao()
    }
}
