package com.weather.lesson9

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.lesson9.databinding.ActivityMainBinding

private const val KEY_STATE = "KEY_STATE"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var state = CounterState(0, "")

    /**
     * @param savedInstanceState если является null то активность запустилась впервые
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Проверим что savedInstanceState не является null
        if (savedInstanceState != null) {
            // версия SDK
            if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // по ключу достанем наше состояние
                state = savedInstanceState.getParcelable(KEY_STATE, CounterState::class.java)
                    ?: error("Нет состояния")
            } else {
                state = savedInstanceState.getParcelable<CounterState>(KEY_STATE)
                    ?: error("Нет состояния")
            }
        }

        renderTextView()

        binding.buttonIncrement.setOnClickListener {
            state = state.increment()
            renderTextView()
        }

        binding.buttonDecrement.setOnClickListener {
            state = state.decrement()
            renderTextView()
        }

        binding.buttonNextSreen.setOnClickListener {
            /*
             Параметры :
             1. Контекст - контекст это класс который предоставляет доступ к систменым сервисам,
             к ресурсам приложения, получить информацию о приложении как например packageName, используется
             для любой работы с компонентами Android SDK, также позволяет запускать компоненты
             2. Тип класс
             */
            val startActivityIntent = Intent(this, SecondActivity::class.java)
            // Передача данных в другую активность
            startActivityIntent.putExtra(SecondActivity.KEY_MESSAGE, state.count)
            // запуск Активности
            startActivity(startActivityIntent)
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
        outState.putParcelable(KEY_STATE, state)
        // outState.putSerializable(KEY_STATE, state)
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
        binding.textView.text = state.count.toString()
    }
}