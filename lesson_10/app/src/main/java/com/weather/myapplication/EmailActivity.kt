package com.weather.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.myapplication.databinding.ActivityEmailBinding

class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEmailParamsFromIntent()
    }

    private fun setEmailParamsFromIntent() {
        // получение данных из интентов
        val addresses = intent.getStringArrayExtra(Intent.EXTRA_EMAIL)
        val subject = intent.getStringExtra(Intent.EXTRA_SUBJECT)

        binding.textViewAdress.text = addresses?.joinToString() ?: ""
        binding.textViewSubject.text = subject ?: ""
    }
}
