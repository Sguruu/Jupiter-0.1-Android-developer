package com.weather.lessonsix

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        button.setOnClickListener {
            Toast.makeText(this, resources.getString(R.string.text_example), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initView() {
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
    }
}


