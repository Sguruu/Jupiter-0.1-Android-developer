package com.weather.ls_22

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.weather.ls_22.databinding.FragmentFlowBasicBinding
import com.weather.ls_22.utils.textChangedFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

class FlowBasicFragment : Fragment(R.layout.fragment_flow_basic) {
    private var _binding: FragmentFlowBasicBinding? = null
    private val binding get() = _binding!!

    private var currentJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlowBasicBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val generator = createFlowGenerator()

        binding.newFragmentButton.setOnClickListener {
            this.findNavController()
                .navigate(R.id.action_flowBasicFragment_to_flowOperatorsFragment)
        }

        binding.startEmitCancelFlowButton.setOnClickListener {
            // отмена текущей запущеной корутины
            currentJob?.cancel()
            startEmit(generator)
        }

        binding.startEmitButton.setOnClickListener {
            startEmit(generator)
        }

        binding.emitCallbackFlowButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                flowFromCallback().collect {
                    Log.d("MyTest", "collect $it")
                    binding.textView.text = it
                }
            }
        }

        binding.errorButton.setOnClickListener {
            errorHandling()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun flowFromCallback(): Flow<String> {
        // создаем flow используя диспетчер callbackFlow
        return binding.editText.textChangedFlow()
    }

    private fun flowOfBuilders() {
        // принимает в параметрах различные количества элементов
        flowOf(1, 2, 3)

        // экстеншен функция asFlow которая позволяет преобразовать объект во flow
        // пример использования
        val flowFromSuspendLambda = suspend {
            delay(1000)
            10
        }.asFlow()

        (0..100).asFlow()

        arrayOf(123, 321).asFlow()
        listOf(123, 321).asFlow()
        setOf(123, 321).asFlow()
        // *
    }

    private fun startEmit(currentFlow: Flow<Int>) {
        //  создание корутины в скоупе фрагмента
        currentJob = viewLifecycleOwner.lifecycleScope.launch {
            currentFlow
                // трансформируем наши данные
                .map {
                    "Рандомное число : $it"
                }
                // для получения значения из флоу используем терминальный метод
                // value - значение из потока
                .collect { value ->

                    Log.d("MyTest", "collect $value")
                    binding.textView.text = value
                }
        }
    }

    private fun createFlowGenerator(): Flow<Int> {
        // для создания Flow необходимо использовать билдер
        val flow = flow<Int> {
            Log.d("MyTest", "start flow")
            while (true) {
                val value = Random.nextInt()
                Log.d("MyTest", "emit value = $value")
                // излучать значение во Flow/поток
                emit(value)
                // задержка, приостановка выполнения на 1 секунду
                delay(1000)
            }
        }
        return flow
    }

    private fun errorHandling() {
        flow {
            delay(1000)
            emit(1)
        }
            // обрабатывает ошибки возникающие выше по цепочке
            .catch {
                // любой код
            }
            .catch { emit(1) }
            .map { error("Ошибка в errorHandling") }
            //   .catch { throw it } выкинуть исключение дальше по цепочки
            .catch { }
            .onEach { Log.d("MyTest", "element = $it") }
            // указывает скоуп выполнения
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
