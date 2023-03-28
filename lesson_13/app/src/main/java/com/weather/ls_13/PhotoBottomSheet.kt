package com.weather.ls_13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weather.ls_13.databinding.DialogButtomSheetBinding

class PhotoBottomSheet : BottomSheetDialogFragment() {

    private var _binding: DialogButtomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogButtomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewAddPhoto.setOnClickListener {
            showToast(binding.textViewAddPhoto.text.toString())
        }

        binding.deletePhoto.setOnClickListener {
            showToast(binding.deletePhoto.text.toString())
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}
