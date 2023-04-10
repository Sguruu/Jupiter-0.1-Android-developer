package com.weather.ls_15.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weather.ls_15.R
import com.weather.ls_15.databinding.FragmentDetailInfoBinding

class DetailsFragment : Fragment(R.layout.fragment_detail_info) {
    private var _binding: FragmentDetailInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString(KEY_NAME)
        val age = arguments?.getInt(KEY_AGE)

        name?.let {
            binding.nameTextView.text = it
        }
        age?.let {
            binding.ageTextView.text = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_NAME = "name"
        const val KEY_AGE = "ages"
    }
}
