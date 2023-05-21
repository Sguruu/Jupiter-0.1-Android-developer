package com.weather.ls_23

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.weather.ls_23.databinding.FragmentServicesBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FragmentServices : Fragment(R.layout.fragment_services) {
    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        binding.backgroundServiceButton.setOnClickListener {
            startService()
        }
        binding.foregroundServiceButton.setOnClickListener {
            startForegroundService()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        DownloadState.downloadState
            .onEach { isLoading ->
                binding.backgroundServiceButton.isVisible = !isLoading
                binding.progressBar.isVisible = isLoading
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    // запуск сервиса
    private fun startService() {
        val downloadIntent = Intent(requireContext(), DownloadService::class.java)
        requireContext().startService(downloadIntent)
    }

    private fun startForegroundService() {
        Log.d("MyTest", "startForegroundService")
        val downloadIntent = Intent(requireContext(), ForegroundDownloadService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireContext().startForegroundService(downloadIntent)
        }
    }
}
