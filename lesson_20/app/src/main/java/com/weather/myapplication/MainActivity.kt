package com.weather.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playSoundButton.setOnClickListener {
            MediaPlayer.create(this, R.raw.sound)
                // запускаем проигрыватель
                .start()
        }

        // Открытие потока данных для чтения необработанного ресурса
        resources.openRawResource(R.raw.sound).use {
            // блок кода
        }
    }
}
