package com.weather.lesson9

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.lesson9.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    companion object {
        const val KEY_MESSAGE = "KEY_MESSAGE"
    }

    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // получить данные
        val textStateOneActivity = intent.getIntExtra(KEY_MESSAGE, 0).toString()

        binding.textView.text = textStateOneActivity
    }
}
