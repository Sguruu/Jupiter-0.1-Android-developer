package com.weather.myapplication

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.weather.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // проверяем на успешность завершения
            if (result.resultCode == Activity.RESULT_OK) {
                // из Intenta можно получить только превью фотографии, а чтобы работать с полно-раз
                // фотографией нам нужно рабоать с файлами и разрашениями об этом позже
                val data: Intent? = result.data
                val previewBitmap: Bitmap?
                if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // по соглашинею наша фотография лежит по ключу data и имеет тип Bitmap
                    /*
                    Что такое Bitmap ? Любое растовое изображение это набор пикселей, информация
                    о изображении например цвет или прозрачность можно сохранить в битах, BitMap -
                    класс который позволяет хранит точки изображений и информацию по каждой из этих
                    точек
                     */
                    previewBitmap = data?.getParcelableExtra("data", Bitmap::class.java)
                } else {
                    previewBitmap = data?.getParcelableExtra("data") as? Bitmap
                }
                // установки битмаба, если он будет null то imageView просто очиститься
                binding.imageViewPhoto.setImageBitmap(previewBitmap)
            } else {
                toast("Фотография не сделана")
            }
        }

    private var test2 = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOpenEmailClient.setOnClickListener {
            val emailAddress = binding.editTextEmail.text.toString()
            val emailSubject = binding.editTextEmailSubject.text.toString()

            // проверим введена ли почта корректно, для этого используем регулярное выражение
            // matches если строка соствествует регулярному выражению
            val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()

            if (!isEmailValid) {
                toast(resources.getString(R.string.entered_an_invalid_email))
            } else {
                // запустим почтовое приложение, для этого запускаем нужный action
                // его найти можем тут https://developer.android.com/guide/components/intents-common
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    /*
                    позволяет передать данные над которыми будет производиться данное действие
                    uri унифицированный индификатор ресурсов, он представляет ссылку на данные,
                    в нашем случае он просто содержит схему, которое говорит что мы хотим открыть
                    приложение для эмейла, в качестве uri можно и использовать другие примеры (схемы
                    это можно посмотреть в документации)
                     */
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                    putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                }

                try {
                    startActivity(emailIntent)
                } catch (e: ActivityNotFoundException) {
                    toast("Нет нужного компонента")
                }
            }
        }

        binding.buttonTakePhoto.setOnClickListener {
            // проверка наличия доступа к камере
            val isCameraPermissionGranted =
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED

            if (!isCameraPermissionGranted) {
                // запрос разрешения камеры
                ActivityCompat.requestPermissions(this, arrayOf(CAMERA), 1)
            } else {
                // создаим intent чтобы открыть камеру
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                // убедимся что приложение существует
                try {
                    resultLauncher.launch(cameraIntent)
                } catch (e: ActivityNotFoundException) {
                    toast("Нет нужного компонента")
                }
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
