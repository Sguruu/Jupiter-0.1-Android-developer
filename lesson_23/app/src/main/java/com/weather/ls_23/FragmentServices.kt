package com.weather.ls_23

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
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
        binding.downloadButton.setOnClickListener {
            startDownload()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startDownload() {
        val urlToDownload = binding.editText.text.toString()

        // создания объекта, для передачи данных
        val workData = workDataOf(
            // в качестве значаения можно передавать примитивы, строки и их массивы
            // есть ограничение на размер это 10 килобайт, если данных больше, то их можно положить
            // в БД или в файл
            DownloadWorker.DOWNLOAD_URL_KEY to urlToDownload // аналогичный синтаксис Pair(DownloadWorker.DOWNLOAD_URL_KEY, urlToDownload)
        )

        // настроен как будет выполняться работать
        val workRequest =
            // создадим работу, которая будет совершаться один раз
            OneTimeWorkRequestBuilder<DownloadWorker>()
                // отправка данных в worker
                .setInputData(workData)
                .build()

        // отправляем работу на выполнение
        WorkManager.getInstance(requireContext())
            // ставит в очередь выполнение workRequest
            .enqueue(workRequest)

        WorkManager.getInstance(requireContext())
            // вернет LiveData
            .getWorkInfoByIdLiveData(workRequest.id)
            // подпишемся на LiveData
            .observe(viewLifecycleOwner, {
                observeWorkInfo(it)
            })
    }

    private fun observeWorkInfo(workInfo: WorkInfo) {
        // получаем состояние воркера
        val isFinished = workInfo.state.isFinished

        binding.progressBar.isVisible = !isFinished
        binding.cancelDownloadButton.isVisible = !isFinished
        binding.downloadButton.isVisible = isFinished

        if (isFinished) {
            Toast.makeText(
                requireContext(),
                "work завершился с состоянием = ${workInfo.state}",
                Toast.LENGTH_SHORT
            ).show()
        }
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
