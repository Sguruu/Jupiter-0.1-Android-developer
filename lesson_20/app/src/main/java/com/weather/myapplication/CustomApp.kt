package com.weather.myapplication

import android.app.Application

class CustomApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // скачаем на debug сборке
//        if (BuildConfig.DEBUG) {
//            // StrictMode - набор инструментов которые позволят оповестить нас о каких - либо
//            // ошибках
//            StrictMode.setThreadPolicy(
//                // принимает в себя политику проверки потоков
//                StrictMode.ThreadPolicy.Builder()
//                    // проверка операций чтения с диска
//                    .detectDiskReads()
//                    // запись на диск
//                    .detectDiskWrites()
//                    // работа с интернет соеденением
//                    .detectNetwork()
//                    // дейтсвия при обнаружении работы в потоках выше вызывам ошибку
//                    .penaltyDeath()
//                    .build()
//            )
//        }
    }
}
