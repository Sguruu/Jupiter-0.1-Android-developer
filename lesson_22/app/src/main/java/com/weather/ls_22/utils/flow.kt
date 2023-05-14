package com.weather.ls_22.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChangedFlow(): Flow<String> {
    return callbackFlow<String> {
        val textChangedListener = object : TextWatcher {
            // до изменения текста
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // заглушка
            }

            // при изменении текста
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // send запускается только в suspend функции
                //  send(s?.toString().orEmpty())
                // позволяет emit значения во flow
                trySendBlocking(s?.toString().orEmpty())
                    .onSuccess {
                        Log.d("MyTest", "onSuccess")
                    }
                    .onFailure {
                        Log.d("MyTest", "onFailure")
                    }
            }

            // после изменения текста
            override fun afterTextChanged(s: Editable?) {
                // заглушка
            }
        }

        this@textChangedFlow.addTextChangedListener(textChangedListener)

        // вызывается тогда, когда наш flow отменяется
        awaitClose {
            Log.d("MyTest", "awaitClose")
            this@textChangedFlow.removeTextChangedListener(textChangedListener)
        }
    }
}
