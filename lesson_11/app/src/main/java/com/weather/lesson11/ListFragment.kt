package com.weather.lesson11

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.weather.lesson11.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.let { it as ViewGroup }
            // обращение к дочерним элементам
            .children
            // приобразуем в кнопки наши дочерние элеметы, если Null мы пропустим
            .mapNotNull { it as? Button }
            .forEach { button ->
                button.setOnClickListener {
                    Log.d("MyTest", button.text.toString())
                    // обращаемся к активности
                    (activity as HostActivity).onItemSelect(button.text.toString())
                }
            }
    }
}
