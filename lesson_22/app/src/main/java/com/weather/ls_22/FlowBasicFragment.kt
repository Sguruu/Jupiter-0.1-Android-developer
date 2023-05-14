package com.weather.ls_22

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.weather.ls_22.databinding.FragmentFlowBasicBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

        binding.startEmitCancelFlowButton.setOnClickListener {
            // отмена текущей запущеной корутины
            currentJob?.cancel()
            startEmit(generator)
        }

        binding.startEmitButton.setOnClickListener {
            startEmit(generator)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}
