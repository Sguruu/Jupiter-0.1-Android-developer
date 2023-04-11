package com.weather.ls_16

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.weather.ls_16.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainActivityViewModel>()
    private var isTimerStart = false
    private lateinit var threadTimer: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.threadsButton.setOnClickListener {
            if (isTimerStart) {
                threadTimer.interrupt()
            } else {
                startTimer()
            }
            isTimerStart = !isTimerStart
        }

        viewModel.timeLiveData.observe(this) {
            supportActionBar?.title = it.toString()
        }
    }

    private fun startTimer() {
        var time: Int = 0
        threadTimer = Thread {
            try {
                while (true) {
                    // вызовет ошибку
                    // supportActionBar?.title = (time++).toString()
                    // Приостановка потока на 2 секунду
                    viewModel.updateLiveData(time++)
                    Thread.sleep(1000)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        threadTimer.start()
    }
}
