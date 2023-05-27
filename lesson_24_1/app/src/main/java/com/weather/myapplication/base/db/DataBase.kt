package com.weather.myapplication.base.db

import android.content.Context
import androidx.room.Room
import com.weather.myapplication.base.db.database.WeatherDataBase

object DataBase {
    // для создания инстанса нужен контекст, который мы не можем получить в этом классе, по этому
    // укажем ленивую инициализацию
    lateinit var instance: WeatherDataBase
        private set

    /**
     * Функция инициализатор DataBase
     * @param context контекст приложения
     */
    fun init(context: Context) {
        // будет сохранять на диск
        instance = Room.databaseBuilder(
            context,
            WeatherDataBase::class.java,
            WeatherDataBase.DB_NAME
        ).build()

        // Room.inMemoryDatabaseBuilder() - сохраняет только в оперативную память,
        // при перезапуске данные не сохранятся
    }
}
