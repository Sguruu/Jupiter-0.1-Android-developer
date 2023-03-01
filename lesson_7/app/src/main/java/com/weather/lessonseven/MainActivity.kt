package com.weather.lessonseven

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.weather.lessonseven.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Новый текст из кода"

        binding.toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Стрелка назад нажата", Toast.LENGTH_SHORT).show()
        }
    }
}
