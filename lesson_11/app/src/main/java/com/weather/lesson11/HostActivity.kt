package com.weather.lesson11

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.weather.lesson11.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity(), IHost {

    private lateinit var binding: ActivityHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Проверка", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelect(value: String) {
        Toast.makeText(this, "Проверка", Toast.LENGTH_SHORT).show()
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView2.id, InfoFragment.newInstance(value))
            .commit()
    }
}
