package com.weather.ls_19_1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.weather.ls_19_1.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val scope = CoroutineScope(Dispatchers.Main)

    // создаем обаботчик ошибки для корутины
    // лямбда CoroutineExceptionHandler будет вызываться в тот момент когда произошла ошибка
    // throwable - ошибка возникшая в ходе выполнения
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("MyTest", "Ошибка CoroutineExceptionHandler", throwable)
    }

    // добавляем в scope обработку errorHandler
    // корутина которая будет запущена в этом скоупе, при возникновении ошибки в ней будет обработана
    // errorHandlerOM
    private val scopeHandler = CoroutineScope(Dispatchers.Main + errorHandler)

    // SupervisorJob отличается от обычно Job тем, что при возникновении ошибки в дочерней джобе, не
    // произодейт отмена родительской джобы
    private val scopeHandlerSupervisorJob =
        CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        binding.crashButton.setOnClickListener {
            try {
                scope.launch {
                    error("Тест ошибки")
                }
            } catch (e: Throwable) {
            }
        }

        binding.errorHandlingButton.setOnClickListener {
            scope.launch {
                try {
                    error("Тест ошибки")
                } catch (e: Throwable) {
                }
            }
        }

        binding.errorCoroutineExceptionHandlerButton.setOnClickListener {
            scopeHandler.launch {
                error("Текст ошибки")
            }
        }

        binding.cancelParentCoroutineErrorButton.setOnClickListener {
            scopeHandler.launch {
                delay(3000)
                error("Тестовая ошибка")
            }
            scopeHandler.launch {
                var i = 0
                while (true) {
                    i++
                    delay(500)
                    Log.d("MyTest", "log $i")
                }
            }
        }

        binding.supervisorJobButton.setOnClickListener {
            scopeHandlerSupervisorJob.launch {
                delay(3000)
                error("Тестовая ошибка")
            }
            scopeHandlerSupervisorJob.launch {
                var i = 0
                while (true) {
                    i++
                    delay(500)
                    Log.d("MyTest", "log $i")
                }
            }
        }

        binding.blockThreadCoroutineJobButton.setOnClickListener {
            scopeHandlerSupervisorJob.launch {
                var i = 0
                // переключим Dispatchers чтобы не заблокировать основной поток
                withContext(Dispatchers.Default) {
                    suspendCancellableCoroutine<Unit> { cancellableContinuation ->
                        cancellableContinuation.invokeOnCancellation {
                            // обработка отмены (отмена запроса, изменение флага и так далее
                            Log.d("MyTest", "Запуск блока кода обработки отмены")
                        }
                    }

                    // isActive будет запускать цикл только когда scope активна (обработка отмены)
                    while (isActive) {
                        // еще один способ отменить корутину это использовать метод yield(), он
                        // будет выбрасывать исключение если корутина отменена

                        i++
                        // блокирует поток в котором запускается корутина
                        Thread.sleep(500)
                        Log.d("MyTest", "log $i")
                    }
                }
            }
        }

        binding.cancelBlockThreadCoroutineJobButton.setOnClickListener {
            // отменяет все запущеные корутины или Job, не сможем запускать новые корутины
            // scopeHandlerSupervisorJob.cancel()
            // отмена всех дочерних корутин
            scopeHandlerSupervisorJob.coroutineContext.cancelChildren()
        }
    }
}
