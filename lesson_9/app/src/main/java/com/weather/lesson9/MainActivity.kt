package com.weather.lesson9

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.lesson9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var state = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        renderTextView()
        binding.buttonIncrement.setOnClickListener {
            state++
            renderTextView()
        }

        binding.buttonDecrement.setOnClickListener {
            state--
            renderTextView()
        }
    }

    private fun renderTextView() {
        binding.textView.text = state.toString()
    }
}
