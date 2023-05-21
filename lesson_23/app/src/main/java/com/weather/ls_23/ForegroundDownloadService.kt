package com.weather.ls_23

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.*

class ForegroundDownloadService() : Service() {
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
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        // стартует оповещение для нашего сервиса
        // на вход принимает индификатор оповещения и само оповещение
        startForeground(NOTIFICATION_ID, createNotification(0, 0, "Прогресс загрузки"))
        coroutineScope.launch {
            Log.d("MyTest", "download started")
            DownloadState.changeDownloadState(true)
            // эмуляция прогресса загрузки
            val maxProgress = 5
            (0 until maxProgress).forEach {
                Log.d("MyTest", "downloadProgress = ${it + 1}/$maxProgress")
                val updatedNotification =
                    createNotification(it + 1, maxProgress, "Прогресс загрузки")
                // отображание новой нотификации
                notificationManagerCompat.notify(NOTIFICATION_ID, updatedNotification)
                delay(1000)
            }
            Log.d("MyTest", "download complete")
            DownloadState.changeDownloadState(false)

            notificationManagerCompat.notify(
                NOTIFICATION_ID,
                createNotification(maxProgress, maxProgress, "Загрузка завершена")
            )
            // завершение нотификации
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                stopForeground(STOP_FOREGROUND_DETACH)
            } else {
                // устарел начиная с Android 11
                stopForeground(true)
            }

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

    /**
     * Создание оповещения
     * @param progress - текущий прогресс
     * @param maxProgress - максимальный прогесс
     */

    private fun createNotification(progress: Int, maxProgress: Int, title: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            // установка иконки
            .setSmallIcon(android.R.drawable.stat_sys_download)
            // третий параметр, указывает, что мы точно знаем прогресс
            .setProgress(maxProgress, progress, false)
            // установка однократного оповещения
            .setOnlyAlertOnce(true)
            .build()
    }

    companion object {
        const val NOTIFICATION_ID = 134
        const val CHANNEL_ID = "ForegroundService Kotlin"
    }
}
