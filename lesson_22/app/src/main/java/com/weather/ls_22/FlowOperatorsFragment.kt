package com.weather.ls_22

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.weather.ls_22.databinding.FragmentFlowOperatorsBinding
import com.weather.ls_22.model.Gender
import com.weather.ls_22.model.User
import com.weather.ls_22.utils.checkedChangesFlow
import com.weather.ls_22.utils.textChangedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowOperatorsFragment : Fragment(R.layout.fragment_flow_operators) {
    private var _binding: FragmentFlowOperatorsBinding? = null
    private val binding get() = _binding!!

    private val listOfUserse = listOf<User>(
        User(1, "Анна Ивановна", 24, Gender.FEMALE),
        User(2, "Петр Иванов", 34, Gender.MALE),
        User(3, "Тяпа Тяпков", 64, Gender.MALE),
        User(4, "Оксана Светлановна", 14, Gender.FEMALE)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlowOperatorsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowOperators()
    }

    private fun flowOperators() {
        viewLifecycleOwner.lifecycleScope.launch {
            combine(
                binding.checkBox.checkedChangesFlow()
                    .onStart { emit(false) },
                binding.editText.textChangedFlow()
                    .onStart { emit("") },
                // функция объединения двух flow, где onlyFemale значение из checkBox, второе значения
                // text которое приходит из editText
                { onlyFemale, text ->
                    // превратим наши значения в пару, чтобы было удобнее их использовать
                    onlyFemale to text // анологично записи Pair(onlyFemale,text)
                }
            )
                // sample выдает только одно значение в указанный промежуток времени
                .debounce(400)
                // отфильтровывает одинаковые значения
                .distinctUntilChanged()
                .onEach {
                    binding.textView.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("MyTest", "старт поиска = $it")
                }
                // позволяет отменять текущий запрос и совершать новый
                .mapLatest { (onlyFemale, text) ->
                    // вставляем вводимый символ
                    searchUsers(onlyFemale, text)
                }
                .onEach {
                    binding.textView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    Log.d("MyTest", "конец поиска = $it")
                }
                // преобразуем в строку пользователей
                .map {
                    it.map { it.toString() }
                        // объединяем все в одну строку и каждый новый пользователь начинается с
                        // новой строки
                        .joinToString("\n")
                }
                .collect {
                    binding.textView.text = it
                }
        }
    }

    // представим что это функция возвращает результат запроса на сервер
    private suspend fun searchUsers(onlyFemale: Boolean, queru: String): List<User> {
        // задержка 1 секунда
        delay(1000)
        return listOfUserse.filter {
            // проверка установлено ли значение в чек боксе
            if (!onlyFemale) {
                // фильтр только по имени
                it.name.contains(queru, ignoreCase = true)
            } else {
                // фильтр по имени и полу
                it.name.contains(queru, ignoreCase = true) && it.gender == Gender.FEMALE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
