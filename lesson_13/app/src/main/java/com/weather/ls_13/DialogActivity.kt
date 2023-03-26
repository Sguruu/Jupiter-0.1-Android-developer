package com.weather.ls_13

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weather.ls_13.databinding.ActivityDialogBinding

class DialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogBinding

    private var dialog: AlertDialog? = null

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

        binding.buttonShowCustomDialog.setOnClickListener {
            showCustomDialog()
        }
    }

    private fun showSimpleDialog() {
        dialog = AlertDialog.Builder(this)
            .setTitle("Простой диалог")
            .setMessage("Простой диалог сообщение")
            // конфигурация диалога из билдера и получение инстанса диалога
            .create()

        // отображение диалога
        dialog?.show()
    }

    private fun showDialogButton() {
        dialog = AlertDialog.Builder(this)
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
            .create()

        dialog?.show()
    }

    private fun showSingleChoiceDialog() {
        val arrayStudents = arrayOf("Варя", "Герман", "Саша", "Валера")
        dialog = AlertDialog.Builder(this)
            .setTitle("Выберите ученика")
            .setItems(arrayStudents) { _, witch ->
                showToast("Был выбран ${arrayStudents[witch]}")
            }
            .create()
        //  .show()
        dialog?.show()
    }

    private fun showCustomDialog() {
        dialog = AlertDialog.Builder(this)
            // для установки кастомной view
            .setView(R.layout.dialog_attemption)
            .setPositiveButton("Скоро отдыхать") { _, _ -> }
            .create()
        dialog?.show()
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // скрытие диалога при перевороте
        dialog?.dismiss()
    }
}
