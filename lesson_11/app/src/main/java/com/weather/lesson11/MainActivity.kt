package com.weather.lesson11

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.lesson11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            showInfoFragment()
        }
    }

    private fun showInfoFragment() {
        // спомощью транзакции мы можем добавить, удалить, заменить
        // val transaction = supportFragmentManager.beginTransaction()
        // val infoFragment = InfoFragment()
        /*
        Первый аргумент id нашего контейнера,
        Второй аргумент, класс нашего фрагмента
         */
        //  transaction.add(binding.fragment1.id, infoFragment)
        // для сохранения нашей транзакции и показа его, но она не гарантирует что наш фрагмент
        // появится сразу
        //  transaction.commit()
        // Сразу отобразит наш фрагмент, но не добавится в бэкстэк
        // transaction.commitNow()

        /* другой вид тогоже самого кода */
        supportFragmentManager.beginTransaction()
            .add(binding.fragment1.id, InfoFragment())
            .commit()
    }
}
