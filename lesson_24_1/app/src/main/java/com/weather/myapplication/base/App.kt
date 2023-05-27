package com.weather.myapplication.base

import android.app.Application
import android.content.res.Resources
import com.weather.myapplication.base.db.DataBase
import dagger.hilt.DefineComponent
import dagger.hilt.android.HiltAndroidApp

/*
@HiltAndroidApp - говорит о том, что нужно будет сгенерировать компонент dagger для приложения
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        res = this.resources
        // создаем приложение при старте
        DataBase.init(this)
    }

    companion object {
        lateinit var res: Resources
            private set
    }
}
