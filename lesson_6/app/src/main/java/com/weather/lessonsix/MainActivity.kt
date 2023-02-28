package com.weather.lessonsix

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.weather.lessonsix.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toggleLastNameButton.setOnClickListener {
            binding.group.isVisible = !binding.group.isVisible
        }

        binding.group.referencedIds.forEach {
            findViewById<View>(it).setOnClickListener {
                Toast.makeText(this, "clicked view on group", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
