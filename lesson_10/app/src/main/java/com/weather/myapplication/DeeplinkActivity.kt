package com.weather.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.myapplication.databinding.ActivityDeeplinkBinding

class DeeplinkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeeplinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeeplinkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIntentData()
    }

    private fun handleIntentData() {
        // получим последнюю часть сегмента https://github.com/jupiter/test
        // также можно получить query, port, scheme, путь
        intent.data?.lastPathSegment?.let {
            binding.textViewNameCourse.text = it
        }
    }
}
