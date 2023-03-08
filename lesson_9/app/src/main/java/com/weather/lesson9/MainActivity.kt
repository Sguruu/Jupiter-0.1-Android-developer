package com.weather.lesson9

import android.content.res.Configuration
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.weather.lesson9.databinding.ActivityMainBinding

private const val KEY_STATE = "KEY_STATE"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var state = 0

    /**
     * @param savedInstanceState если является null то активность запустилась впервые
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Проверим что savedInstanceState не является null
        if (savedInstanceState != null) {
            // по ключу достанем наше состояние
            state = savedInstanceState.getInt(KEY_STATE)
        }

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

    /**
     * Метод будет вызываться когда активити будет уничтожаться, без желания пользователя
     * @param outState параметр куда мы сохраняем наше состояние
     */
    override fun onSaveInstanceState(outState: Bundle) {
        // необходимо вызывать
        super.onSaveInstanceState(outState)
        /*
        Метод который используется для сохранеия каких либо значений в Bundle
        Параметрые :
        key - ключ
        value - значение которое мы хотим сохранить
         */
        outState.putInt(KEY_STATE, state)
    }

    /**
     * Метод вызывается только тогда, когда наша активность уже создается не первый раз
     * @param savedInstanceState можем заметить является не null
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // тут можно сделать тоже самое, что мы делали в onCreate
        // по ключу достанем наше состояние
//        state = savedInstanceState.getInt(KEY_STATE)
//        renderTextView()
    }

    /**
     * Ручное обрабатывание изменение конфигураций
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    private fun renderTextView() {
        binding.textView.text = state.toString()
    }
}
