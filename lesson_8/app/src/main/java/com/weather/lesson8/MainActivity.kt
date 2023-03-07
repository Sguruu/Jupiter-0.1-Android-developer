package com.weather.lesson8

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.weather.lesson8.databinding.ActivityMainBinding

private const val TAG = "MyTest"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebugLogger.d(TAG, "onCreate() ${hashCode()}")
        Log.d(TAG, "onCreate() ${hashCode()}")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        // логируются множнество сообщений в процессе работы приложения
//        Log.println(Log.VERBOSE, "MyTest", "Ваше сообщение")
//        // отладочные сообщения
//        Log.println(Log.DEBUG, "MyTest", "Ваше сообщение")
//        // информационные сообщения, которые оповещают программиста о чем-то
//        Log.println(Log.INFO, "MyTest", "Ваше сообщение")
//        // сообщения об ошибках
//        Log.println(Log.ERROR, "MyTest", "Ваше сообщение")
//        // критические ошибки, которые не должны были случиться (самый высокий приоритет)
//        Log.println(Log.ASSERT, "MyTest", "Ваше сообщение")

        binding.imageView.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        DebugLogger.d(TAG, "onStart() ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        DebugLogger.d(TAG, "onResume() ${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        DebugLogger.d(TAG, "onPause() ${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        DebugLogger.d(TAG, "onStop() ${hashCode()}")
    }

    override fun onRestart() {
        super.onRestart()
        DebugLogger.d(TAG, "onRestart() ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugLogger.d(TAG, "onDestroy() ${hashCode()}")
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        DebugLogger.d(TAG, "onTopResumedActivityChanged ${hashCode()} $isTopResumedActivity")
    }

    object DebugLogger {
        fun d(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, message)
            }
        }
    }
}
