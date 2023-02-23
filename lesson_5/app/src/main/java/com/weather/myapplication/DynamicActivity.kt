package com.weather.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DynamicActivity : AppCompatActivity() {

    lateinit var textInput: EditText
    lateinit var addButton: Button
    lateinit var rootLinearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)

        initView()

        addButton.setOnClickListener {
            val textAdd = textInput.text.toString()
            // создает view из ресурса разметки
            /*
            Парамертры :
            1. View которую нужно использовать
            2. Родительская View
            3. Нужно ли нам добавить нашу view в разметку сразу
            true - добавление автоматически в контейнер,
            false - добавление в ручную
            Также, если будет указан true, возвращает контейнер куда будет встраиваться view,
            Также, если будет указано false, вернет именно наш контейнер
            Укажем false так как нужно еще указать текст для только-что созданной view
             */
            val view = layoutInflater.inflate(R.layout.item_text, rootLinearLayout, false)
            view.apply {
                val textView = findViewById<TextView>(R.id.textView)
                val deleteButton = findViewById<Button>(R.id.deleteButton)
                textView.text = textAdd
                deleteButton.setOnClickListener {
                    rootLinearLayout.removeView(this)
                }
            }
            rootLinearLayout.addView(view)
        }
    }

    private fun initView() {
        textInput = findViewById(R.id.textInput)
        addButton = findViewById(R.id.addButton)
        rootLinearLayout = findViewById(R.id.rootLinearLayout)
    }
}
