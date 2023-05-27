package com.weather.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.weather.myapplication.R
import com.weather.myapplication.databinding.FragmentInfoBinding
import com.weather.myapplication.viewmodel.InfoViewModel
import com.weather.myapplication.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment(R.layout.fragment_info) {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoViewModel by viewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()

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
        observeViewModelState()
        val nameCity = arguments?.getString(KEY_NAME_CITY)
        val lon = arguments?.getString(KEY_LON)
        val lat = arguments?.getString(KEY_LAT)
        val imageLink = arguments?.getString(KEY_IMAGE_LINK)

        nameCity?.let {
            binding.cityTextView.text = it
        }

        if (savedInstanceState == null) {
            if (lon != null && lat != null) {
                viewModel.requestWeather(lat, lon)
            }
        }

        imageLink?.let {
            renderView(it)
        }

        activityViewModel.updateIsVisibleSearchAction(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModelState() {
        viewModel.responseWeatherLiveData.observe(viewLifecycleOwner) {
            binding.descriptionTextView.text = resources.getString(
                R.string.forecast,
                it.descriptionWeather
            )
            binding.minTextView.text = resources.getString(
                R.string.min_c,
                it.tempMin.toString()
            )
            binding.maxTextView.text = resources.getString(
                R.string.max_c,
                it.tempMax.toString()
            )
            binding.jsonTextView.text = viewModel.convertWeatherToJson(it)
        }
    }

    private fun renderView(imageLink: String) {
        imageLink.let {
            Glide.with(binding.root)
                .load(it)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.cityImageView)
        }
    }

    companion object {
        const val KEY_NAME_CITY = "nameCity"
        const val KEY_LON = "lon"
        const val KEY_LAT = "lat"
        const val KEY_IMAGE_LINK = "imageLink"
    }
}
