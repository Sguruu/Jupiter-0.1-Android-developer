package com.weather.ls_13

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmationDialogFragment : DialogFragment() {

    /*
    Тут создаем диалог но не показываем
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Удаление элемента")
            .setMessage("Вы уверены что хотите удалить элемент ?")
            .setPositiveButton("Да") { _, _ -> }
            .setNegativeButton("Нет") { _, _ -> }
            .setNeutralButton("Нейтральная") { _, _ -> }
            .create()
    }
}
