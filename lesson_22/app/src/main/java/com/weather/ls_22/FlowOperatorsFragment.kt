package com.weather.ls_22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weather.ls_22.databinding.FragmentFlowOperatorsBinding
import com.weather.ls_22.model.Gender
import com.weather.ls_22.model.User

class FlowOperatorsFragment : Fragment(R.layout.fragment_flow_operators) {
    private var _binding: FragmentFlowOperatorsBinding? = null
    private val binding get() = _binding!!

    private val listOf = listOf<User>(
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
