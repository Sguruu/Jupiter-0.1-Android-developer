package com.weather.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.weather.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playSoundButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                MediaPlayer.create(this@MainActivity, R.raw.sound)
                    // запускаем проигрыватель
                    .start()
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            // Открытие потока данных для чтения необработанного ресурса
            resources.openRawResource(R.raw.sound).use {
                // блок кода
            }

            // свойство assets вернут assetManager
            val assetManager = resources.assets
            // возвращает файлы которые находятся в папке, в параметрах принимает путь до папки
            // если передаем "" значит мы хотим просмотреть все файлы в папке asset
            // может вернут null если совершим ошибку в пути к файлу
            val fileListString = assetManager.list("folder1")
                // если вернет null вернет пустой список
                .orEmpty()
                // объединить строки в одну строку
                .joinToString()

            Log.d("MyTest", "fileListString fileString $fileListString")

            // обертка, аналог try catch
            runCatching {
                // получение контента функции, в параметрах нужно указать путь и имя файла
                val text = assetManager.open("folder1/text.txt")
                    // Создает буферизованный модуль чтения для этого входного потока
                    .bufferedReader()
                    // Выполняет заданную функцию блока на этом ресурсе, а затем корректно закрывает его,
                    // независимо от того, выдано исключение или нет.
                    .use {
                        // считать все данные из файла в одну строку
                        it.readText()
                    }
                Log.d("MyTest", "text open $text")
            }
        }

        // lifecycleScope
        CoroutineScope(Dispatchers.IO).launch {
            // сохраним файл в папку кеша
            val cacheDir = this@MainActivity.cacheDir
            // вывод в Log абсолютного пути папки кеша
            Log.d("MyTest", "cacheDir path = ${cacheDir.absolutePath}")

            // запись файла в папку с кешом
            val cacheFile = File(cacheDir, "test_cache,text")
            runCatching {
                cacheFile.outputStream().buffered().use {
                    it.write("Content in cache file".toByteArray())
                }
            }
        }
    }
}
