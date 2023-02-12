package com.weather.lession2jupiter


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // присвоение индификатора к textView
        val textView = findViewById<TextView>(R.id.textView)

        // устанавливаем текст
        textView.text = """
            Build type = ${BuildConfig.BUILD_TYPE}
            Flavor = ${BuildConfig.FLAVOR}
            VersionCode = ${BuildConfig.VERSION_CODE}
            VersionName = ${BuildConfig.VERSION_NAME}
        """
    }
}
