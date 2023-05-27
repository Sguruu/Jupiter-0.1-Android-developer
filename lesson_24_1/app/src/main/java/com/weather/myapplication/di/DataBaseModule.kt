package com.weather.myapplication.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.weather.myapplication.data.db.dao.CityDao
import com.weather.myapplication.data.db.database.WeatherDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
// указание к какому компоненту пренадлежит данный модуль
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Application): WeatherDataBase {
        // scoped, будет создан только дин раз в течение жизни приложения
        // инстанс переиспользуется
        Log.d("MyTest", "provideDataBase")
        return Room.databaseBuilder(
            context,
            WeatherDataBase::class.java,
            WeatherDataBase.DB_NAME
        ).build()
    }

    @Provides
    fun provideUserDao(db: WeatherDataBase): CityDao {
        // нет scope, новый инстанс будет создан каждый раз при запросе CityDao
        Log.d("MyTest", "provideUserDao")
        return db.CityDao()
    }
}
