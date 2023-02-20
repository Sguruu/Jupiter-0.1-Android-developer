package com.weather.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// AppCompatActivity() класс AndroidSDK() который позволяет нам взаимойдествовать с Android
class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView

    // переопределение метода onCreate который запускается когда создается активность
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Класс R автоматически создается комбилятором, через него получаем доступ ко всем ресурсам
        // Устанавливаем для класса MainActivity контент activity_main
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)

        // устанавливаем текст
        textView.text = "Привет мир"
        // установление текст из ресурса
        textView.setText(R.string.app_name)

        // получение строки из рессурсов
        resources.getString(R.string.app_name)
    }
}
