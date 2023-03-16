package com.weather.lesson11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weather.lesson11.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val KEY_TEXT = "KEY_TEXT"
        fun newInstance(text: String): InfoFragment {
            val args = Bundle()
            args.putString(KEY_TEXT, text)

            val fragment = InfoFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // полуачем наши данные из bandle
        // binding.textView.text = arguments?.getString(KEY_TEXT)
        // если точно знаем что у нас есть данные в нашем Bundle
        binding.textView.text = requireArguments().getString(KEY_TEXT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
