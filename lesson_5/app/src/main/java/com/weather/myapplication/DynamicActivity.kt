package com.weather.myapplication

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

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
            // Для создания TextView, нужно будет использовать конструктор, конструктор небоходимо
            // добавить контекст, но также можно добавлять доп атрибуты и стили, о них поговорим по позже
            // apply функция это лямбда которая применяестя сразу к созданному объекту
            val textViewToAdd = TextView(this).apply {
                // указали текст
                this.text = textAdd
                // даллее необходимо создать размер view используя LayoutParams
                /*
                Здесь можно указать различные атрибуты, ширину, высоту, все элемента, давайте добавим
                ширину и высоту элемента равный wrap_content
                 */
                this.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    this.gravity = when (Random.nextInt(3)) {
                        0 -> Gravity.CENTER
                        1 -> Gravity.END
                        else -> Gravity.START
                    }
                }
            }

            // метод который добавляет view в нашу viewGroup
            rootLinearLayout.addView(textViewToAdd)
        }
    }

    private fun initView() {
        textInput = findViewById(R.id.textInput)
        addButton = findViewById(R.id.addButton)
        rootLinearLayout = findViewById(R.id.rootLinearLayout)
    }
}
