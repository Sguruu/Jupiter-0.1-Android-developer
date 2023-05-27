package com.weather.myapplication.presentation.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES.Q
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.weather.myapplication.R
import com.weather.myapplication.databinding.FragmentAddBinding
import com.weather.myapplication.domain.model.City
import com.weather.myapplication.presentation.viewmodel.AddFragmentViewModel
import com.weather.myapplication.presentation.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val viewModel: AddFragmentViewModel by viewModels()

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // проверяем на успешность завершения
            if (result.resultCode == Activity.RESULT_OK) {
                // мы используем result.data?.data для получения URI изображения
                val uri: Uri? = result.data?.data
                val bitmap: Bitmap?
                if (VERSION.SDK_INT <= Q) {
                    // мы используем MediaStore.Images.Media.getBitmap() для получения объекта Bitmap.
                    // метод getBitmap устарел начиная с api 29
                    bitmap =
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                } else {
                    bitmap = uri?.let {
                        // создание источника изображения
                        val source =
                            ImageDecoder.createSource(requireActivity().contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    }
                }
                // установки битмаба, если он будет null то imageView просто очиститься
                bitmap?.let {
                    binding.imageView.setImageBitmap(it)
                }
            } else {
                toast("Фотография не выбрана")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModel.updateIsVisibleSearchAction(false)
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListener() {
        binding.saveButton.setOnClickListener {
            saveData()
        }
        binding.imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }
    }

    private fun saveData() {
        viewModel.insertCites(
            listOf(
                City(
                    name = binding.nameCityTextView.text.toString(),
                    lat = binding.latTextView.text.toString(),
                    lon = binding.lonTextView.text.toString(),
                    imageLink = ""
                )
            )
        )

        binding.nameCityTextView.setText("")
        binding.latTextView.setText("")
        binding.lonTextView.setText("")

        toast("Город успешно добавлен")
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}
