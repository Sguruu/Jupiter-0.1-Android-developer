package com.weather.ls_16

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.weather.ls_16.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainActivityViewModel>()
    private var isTimerStart = false
    private lateinit var threadTimer: Thread

    private val friend1 = Person("Сержа")
    private val friend2 = Person("Тяпа")

    private lateinit var handler: Handler

    // будет давать возможность отправлять задачи в наш главный поток
    // указываем looper главного потока
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initHandler()

        viewModel.timeLiveData.observe(this) {
            supportActionBar?.title = it.toString()
        }

        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.looper.quit()
    }

    private fun initListener() {
        binding.threadsButton.setOnClickListener {
            if (isTimerStart) {
                threadTimer.interrupt()
            } else {
                startTimer()
            }
            isTimerStart = !isTimerStart
        }
        binding.raceConditionButton.setOnClickListener {
            makeMultithreadingIncrement()
        }
        binding.noRaceConditionButton.setOnClickListener {
            synchronizedMakeMultithreadingIncrement()
        }
        binding.deadLockButton.setOnClickListener {
            deadLock()
        }
        binding.noDeadLockButton.setOnClickListener {
            noDeadLock()
        }

        binding.handlerButton.setOnClickListener {
            runHandler()
        }
        binding.handlerThreadButton.setOnClickListener {
            runHandlerThread()
        }
    }

    private fun runHandlerThread() {
        // инициализируем наш поток, в параметр нужно положить имя потока
        /*
        *  Looper.prepare() и  Looper.loop() создадуться автоматически
        */
        val backgroundThread = HandlerThread("handlerThread")
        // запускаем наш поток
        backgroundThread.start()
        handler = Handler(backgroundThread.looper)

        handler.post {
            Log.d("MyTest", "Execute task from thread = ${Thread.currentThread().name}")
            // создание рандомонго числа
            val randomNumber = Random.nextLong()
            // передача в главный поток
            mainHandler.post {
                Log.d("Handler", "Execute view task from thread = ${Thread.currentThread().name}")
                binding.textView.text = randomNumber.toString()
            }

            // запуск с задержкой
            mainHandler.postDelayed({
                Toast.makeText(this, "Был добавлен $randomNumber", Toast.LENGTH_LONG).show()
            }, 2000)
        }
    }

    private fun runHandler() {
        handler.post {
            Log.d("MyTest", "Execute task from thread = ${Thread.currentThread().name}")
        }
    }

    private fun initHandler() {
        // запустим поток
        Thread {
            // Инициализировать текущий поток как цикл.
            Looper.prepare()

            // Возвращает объект Looper, связанный с текущим потоком.
            //   Looper.myLooper()
            // Возвращает объект MessageQueue, связанный с текущим потоком.
            //  Looper.myQueue()

            /*
            Конструктор по умолчанию связывает этот обработчик с Looper для текущего потока. Если в
            этом потоке нет петлителя, этот обработчик не сможет получать сообщения, поэтому
            генерируется исключение.
             */
            handler = Handler()

            // для того чтобы Looper стал работать необходим вызвать метод
            // запуск очереди сообщений в текущем потоке
            Looper.loop()
            Log.d("MyTest", "End thread = ${Thread.currentThread().name}")
        }.start()
    }

    private fun noDeadLock() {
        val thread1 = Thread {
            friend1.throwBallTo(friend2)
        }
        val thread2 = Thread {
            friend2.throwBallTo(friend1)
        }

        thread1.start()
        thread2.start()

        binding.textView.text = "Результат выполнения программы смотри в логах"
    }

    private fun deadLock() {
        val thread1 = Thread {
            friend1.throwBallToDeadLock(friend2)
        }
        val thread2 = Thread {
            friend2.throwBallToDeadLock(friend1)
        }

        thread1.start()
        thread2.start()

        binding.textView.text = "Результат выполнения программы смотри в логах"
    }

    // пример ошибки Race Condition
    private fun makeMultithreadingIncrement() {
        var value = 0
        // количество потоков
        val threadCount = 100
        // количество опреаций
        val incrementCount = 1000
        // ожидаемое значение
        val expectedValue = value + threadCount * incrementCount

        val listThread = (0 until threadCount).map {
            Thread {
                for (y in 0 until incrementCount) {
                    value++
                }
            }
        }

        listThread.forEach {
            it.start()
        }

        listThread.forEach {
            it.join()
        }

        /* альтернативная запись создания потоков
        (0 until threadCount).map {
            Thread {
                for (y in 0 until incrementCount) {
                    value++
                }
            }.apply {
                start()
            }
        }.map {
            it.join()
        }
         */

        binding.textView.setText("value=$value, expected=$expectedValue")
    }

    private fun synchronizedMakeMultithreadingIncrement() {
        var value = 0
        // количество потоков
        val threadCount = 100
        // количество операций
        val incrementCount = 1000
        // ожидаемое значение
        val expectedValue = value + threadCount * incrementCount

        val listThread = (0 until threadCount).map {
            Thread {
                synchronized(this) {
                    for (y in 0 until incrementCount) {
                        value++
                    }
                }
            }
        }

        listThread.forEach {
            it.start()
        }

        listThread.forEach {
            it.join()
        }
        binding.textView.setText("value=$value, expected=$expectedValue")
    }

    private fun startTimer() {
        var time: Int = 0
        threadTimer = Thread {
            try {
                while (true) {
                    // вызовет ошибку
                    // supportActionBar?.title = (time++).toString()
                    // Приостановка потока на 2 секунду
                    viewModel.updateLiveData(time++)
                    Thread.sleep(1000)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        threadTimer.start()
    }
}
