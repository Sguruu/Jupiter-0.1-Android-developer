package com.weather.lesson3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textView = findViewById<TextView>(R.id.second_textView)
        val count = 5
        val pluralString = resources.getQuantityString(R.plurals.main_quantity_string, count, count)
        textView.text = pluralString
    }
}
