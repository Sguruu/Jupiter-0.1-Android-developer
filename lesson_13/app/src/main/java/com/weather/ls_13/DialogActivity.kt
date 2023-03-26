package com.weather.ls_13

import android.os.Bundle
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
}
