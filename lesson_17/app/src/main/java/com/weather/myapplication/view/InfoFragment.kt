package com.weather.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.weather.myapplication.R
import com.weather.myapplication.databinding.FragmentInfoBinding

class InfoFragment : Fragment(R.layout.fragment_info) {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameCity = arguments?.getString(KEY_NAME_CITY)
        val lon = arguments?.getString(KEY_LON)
        val lat = arguments?.getString(KEY_LAT)
        val imageLink = arguments?.getString(KEY_IMAGE_LINK)

        nameCity?.let {
            binding.cityTextView.text = it
        }
        lon?.let {
            binding.minTextView.text = it
        }
        lat?.let {
            binding.maxTextView.text = it
        }
        imageLink?.let {
            Glide.with(binding.root)
                .load(it)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.cityImageView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_NAME_CITY = "nameCity"
        const val KEY_LON = "lon"
        const val KEY_LAT = "lat"
        const val KEY_IMAGE_LINK = "imageLink"
    }
}
