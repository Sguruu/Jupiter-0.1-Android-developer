package com.weather.lesson9

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.lesson9.databinding.ActivityMainBinding
import com.weather.lesson9.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = resources.getString(R.string.next_screen)
    }
}
