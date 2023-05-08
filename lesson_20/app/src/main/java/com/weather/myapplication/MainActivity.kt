package com.weather.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.weather.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // первый параметр имя файла, второй параметр режим работы
    // получим SharedPreferences
    private val sharedPrefs: SharedPreferences by lazy {
        this.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

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

        binding.saveButton.setOnClickListener {
            saveSharedPreferences()
        }

        binding.loadButton.setOnClickListener {
            loadSharePreferences()
        }
    }

    private fun loadSharePreferences() {
        CoroutineScope(Dispatchers.IO).launch {
            val saveText = sharedPrefs.getString(
                // ключ
                KEY_TEXT,
                // значение по умолчанию если ответа не будет
                null
            )
            withContext(Dispatchers.Main) {
                binding.editText.setText(saveText)
            }
        }
    }

    private fun saveSharedPreferences() {
        CoroutineScope(Dispatchers.IO).launch {
            // получаем значения текста для сохранения из editText
            val textToSave = binding.editText.text.toString()
            // запишем наши данные
            sharedPrefs.edit()
                // отправка данных на сохранения, первый параметр ключ, второй значений
                .putString(KEY_TEXT, textToSave)
                // синхронизируем данные можно для этого использовать два метода apply/commit
                .apply()

            binding.editText.setText("")
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

    companion object {
        private const val SHARED_PREFS_NAME = "SHARED_PREFS_NAME"
        private const val KEY_TEXT = "KEY_TEXT"
    }
}
