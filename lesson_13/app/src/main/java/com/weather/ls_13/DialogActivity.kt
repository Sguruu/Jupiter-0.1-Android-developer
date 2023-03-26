package com.weather.ls_13

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weather.ls_13.databinding.ActivityDialogBinding

class DialogActivity : AppCompatActivity(), IListenerDialog {

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

        binding.buttonShowFragmentDialog.setOnClickListener {
            showFragmentDialog()
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

    private fun showFragmentDialog() {
        ConfirmationDialogFragment()
            // tag используется для того чтобы в будущем найти во фрагмент менеджере и скрыть
            // диалог
            .show(supportFragmentManager, "ConfirmationDialogTag")
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    /**
     * Закрытие программно диалог фрагмента
     */
    private fun hideDialogFragment() {
        supportFragmentManager.findFragmentByTag("ConfirmationDialogTag")
            // если фрагмент нашелся по тегу, проверяем является ли он ConfirmationDialogFragment
            ?.let { it as? ConfirmationDialogFragment }
            // если является мы его закрываем
            ?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        // скрытие диалога при перевороте
        dialog?.dismiss()
    }

    override fun positiveButtonListener() {
        showToast("Нажата кнопка Да")
    }

    override fun negativeButtonListener() {
        showToast("Нажата кнопка Нет")
    }

    override fun neutralButtonListener() {
        showToast("Нажата нейтральная кнопка")
    }
}
