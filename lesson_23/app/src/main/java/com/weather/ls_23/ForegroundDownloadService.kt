package com.weather.ls_23

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
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

                // проверка на разрешение
                if (ActivityCompat.checkSelfPermission(
                        this@ForegroundDownloadService,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return@forEach
                }
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

    /**
     * Метод для создания канала, необходим начиная с Api 26
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val chan =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE)
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
    }

    companion object {
        const val NOTIFICATION_ID = 134
        const val CHANNEL_NAME = "ForegroundServiceChannel"
        const val CHANNEL_ID = "ForegroundService Kotlin"
    }
}
