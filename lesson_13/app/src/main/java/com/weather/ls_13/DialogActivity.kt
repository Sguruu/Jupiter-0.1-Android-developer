package com.weather.ls_13

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weather.ls_13.databinding.ActivityDialogBinding

class DialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSimpleDialog.setOnClickListener {
            showSimpleDialog()
        }

        binding.buttonShowDialogButton.setOnClickListener {
            showDialogButton()
        }

        binding.buttonShowSingleChoiceDialog.setOnClickListener {
            showSingleChoiceDialog()
        }
    }

    private fun showSimpleDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Простой диалог")
            .setMessage("Простой диалог сообщение")
            // конфигурация диалога из билдера и получение инстанса диалога
            .create()

        // отображение диалога
        dialog.show()
    }

    private fun showDialogButton() {
        AlertDialog.Builder(this)
            .setTitle("Удаление элемента")
            .setMessage("Вы уверены что хотите удалить элемент ?")
            .setPositiveButton("Да") { _, _ ->
                showToast("Нажата кнопка Да")
            }
            .setNegativeButton("Нет") { _, _ ->
                showToast("Нажата кнопка Нет")
            }
            .setNeutralButton("Нейтральная") { _, _ ->
                showToast("Нажата нейтральная кнопка")
            }
            // можно вызвать без вызова create()
            .show()
    }

    private fun showSingleChoiceDialog() {
        val arrayStudents = arrayOf("Варя", "Герман", "Саша", "Валера")
        AlertDialog.Builder(this)
            .setTitle("Выберите ученика")
            .setItems(arrayStudents) { _, witch ->
                showToast("Был выбран ${arrayStudents[witch]}")
            }
            .show()
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
