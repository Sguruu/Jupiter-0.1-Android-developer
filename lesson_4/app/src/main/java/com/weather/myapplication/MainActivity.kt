package com.weather.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// AppCompatActivity() класс AndroidSDK() который позволяет нам взаимойдествовать с Android
class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var nameInput: EditText

    // переопределение метода onCreate который запускается когда создается активность
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Класс R автоматически создается комбилятором, через него получаем доступ ко всем ресурсам
        // Устанавливаем для класса MainActivity контент activity_main
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        nameInput = findViewById(R.id.nameInput)

        // слушатель ввода
        nameInput.addTextChangedListener(object : TextWatcher {
            // до изменения текста
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            // при изменении текста
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // p0 - то что пользователь ввел, можно работать как со строкой
                // p1 - место где у пользователя находится указатель
                // p2 - длина текста
                // p3 - сколько символо вводит пользователь

                // takeIf условие if
//                textView.text = p0?.takeIf { it.isNotBlank() }
//                    ?.let { name -> resources.getString(R.string.hello_string, name) }

                // аналогичная запись
                if (p0 != null && p0.isNotBlank()) {
                    textView.text = resources.getString(R.string.hello_string, p0)
                } else {
                    textView.text = ""
                }
            }

            // после изменения текста
            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}
