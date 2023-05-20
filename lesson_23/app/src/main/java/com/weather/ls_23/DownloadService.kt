package com.weather.ls_23

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class DownloadService() : Service() {
    // скоуп для корутин
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    // метод связывает сервис с другим компонентом Android (для привязки ко view например)
    // так как мы просто запустим сервис, может вернуть null
    override fun onBind(intent: Intent?): IBinder? = null

    // метод жц сервиса, вызывается при первом запуске сервиса
    override fun onCreate() {
        super.onCreate()
        Log.d("MyTest", "onCreate from ${Thread.currentThread().name}")
    }

    // метод жц, будет вызываться каждый раз, когда будет вызываться метод starService
    // тут пишут код для выполнение какой - либо задачи в сервисе
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyTest", "onStartCommand from ${Thread.currentThread().name}")
        downloadFile()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun downloadFile() {
        coroutineScope.launch {
            Log.d("MyTest", "download started")
            DownloadState.changeDownloadState(true)
            // эмуляция прогресса загрузки
            val maxProgress = 5
            (0 until maxProgress).forEach {
                Log.d("MyTest", "downlodaProgress = ${it + 1}/$maxProgress")
                delay(1000)
            }
            Log.d("MyTest", "download complete")
            DownloadState.changeDownloadState(false)
            // завершение сервиса
            stopSelf()
        }
    }

    // метод жц, вызывается при завершении сервиса
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyTest", "onDestroy")
        // очиста корутин Scope
        coroutineScope.cancel()
    }
}
