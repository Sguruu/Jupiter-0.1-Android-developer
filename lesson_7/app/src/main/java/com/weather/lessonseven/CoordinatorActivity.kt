package com.weather.lessonseven

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.lessonseven.databinding.ActivityCoordinatorBinding

class CoordinatorActivity : AppCompatActivity() {

    lateinit var binding: ActivityCoordinatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoordinatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
