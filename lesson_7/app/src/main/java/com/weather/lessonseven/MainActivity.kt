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

        binding.toolbar.setOnMenuItemClickListener {
            // через it можем получить доступ ко всем элементам меню и их атрибутам
            // получаем id элемента по которому кликнули  it.itemId
            when (it.itemId) {
                R.id.action_1 -> {
                    // означает что мы отработали нажатие на элемент меню
                    toast("action 1 clicked")
                    true
                }
                R.id.action_2 -> {
                    toast("action 2 clicked")
                    true
                }
                else -> false
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
