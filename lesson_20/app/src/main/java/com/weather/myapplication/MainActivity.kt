package com.weather.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
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

        binding.cacheDirButton.setOnClickListener {
            cacheDirClick()
        }

        binding.fileDirButton.setOnClickListener {
            fileDirClick()
        }

        binding.externalFileDirButton.setOnClickListener {
            externalStorage()
        }
    }

    private fun fileDirClick() {
        // lifecycleScope
        CoroutineScope(Dispatchers.IO).launch {
            // сохраним файл в папку кеша
            val fileDir = this@MainActivity.filesDir
            // вывод в Log абсолютного пути папки кеша
            Log.d("MyTest", "fileDir path = ${fileDir.absolutePath}")

            // запись файла в папку с кешом
            val file = File(fileDir, "test_file,text")
            runCatching {
                file.outputStream().buffered().use {
                    it.write("Content in file".toByteArray())
                }
            }
        }
    }

    private fun cacheDirClick() {
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

    private fun externalStorage() {
        CoroutineScope(Dispatchers.IO).launch {
            // проверка на сосояние, если условие верно то мы выходим из выполнения корутины
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch

            // работа с external Storage
            // получаем путь к папке
            val testFolder = this@MainActivity.getExternalFilesDir("testFolder")
            // создаем файл в папке
            val testFile = File(testFolder, "external_test_file.txt")

            runCatching {
                // создаем текс в файле
                testFile.outputStream().buffered().use {
                    it.write("Content in file".toByteArray())
                }
            }
        }
    }
}
