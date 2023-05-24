package com.weather.ls_23

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

// Worker работа без корутин
// CoroutineWorker позволяет нам работать с корутинами
class DownloadWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    /**
     * @return результат выполнения
     * success если задача выполнена успешно
     * retry если задача выполнена не успешно и ее стоит повторить
     * failure если задача выполнена не успешно и повторять не нужно
     */
    override suspend fun doWork(): Result {
        // получение данных по ключу
        val uriToDownload = inputData.getString(DOWNLOAD_URL_KEY)
        Log.d("MyTest", "work started")
        Log.d("MyTest", "work uriToDownload $uriToDownload")
        delay(1000)
        return Result.success()
    }

    companion object {
        const val DOWNLOAD_URL_KEY = "DOWNLOAD_URL"
    }
}
