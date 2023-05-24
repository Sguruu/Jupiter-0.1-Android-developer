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
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
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
        binding.cancelDownloadButton.setOnClickListener {
            stopDownload()
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

        // создаем ограничения
        val workConstraints = Constraints.Builder()
            /* виды NetworkType
            NOT_REQUIRED - сеть не требуется
            CONNECTED - Для этой работы требуется любое работающее сетевое соединение
            UNMETERED - Для этой работы требуется безлимитное сетевое подключение
            METERED - Для этой работы требуется лимитное сетевое подключение.
             */
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()

        // настроен как будет выполняться работать
        val workRequest =
            // создадим работу, которая будет совершаться один раз
            OneTimeWorkRequestBuilder<DownloadWorker>()
                // отправка данных в worker
                .setInputData(workData)
                .setConstraints(workConstraints)
                .build()

        // отправляем работу на выполнение
        WorkManager.getInstance(requireContext())
            // ставит в очередь выполнение workRequest
            // .enqueue(workRequest)
            // ставит в очередь выполнение workRequest и принимает в себя дополнительные параметры
            /*
            дополнительные параметы :
            uniqueWorkName - Идентификатор
            existingWorkPolicy - стратегия работы, что делать если work с таким именем уже
            существует, есть четыре стратегии :
            REPLACE - заменить
            KEEP - Ничего не делать
            APPEND - поставить в очередь и тд, подробнее в описании класса ExistingWorkPolicy
             */
            .enqueueUniqueWork(DOWNLOAD_WORK_ID, ExistingWorkPolicy.REPLACE, workRequest)

        WorkManager.getInstance(requireContext())
            // вернет LiveData
            //   .getWorkInfoByIdLiveData(workRequest.id)
            // возвращает LiveData по индификатору воркера
            .getWorkInfosForUniqueWorkLiveData(DOWNLOAD_WORK_ID)
            // подпишемся на LiveData
            .observe(viewLifecycleOwner, {
                observeWorkInfo(it.first())
            })
    }

    private fun stopDownload() {
        // отменим работу по ID
        WorkManager.getInstance(requireContext()).cancelUniqueWork(DOWNLOAD_WORK_ID)
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

    companion object {
        private const val DOWNLOAD_WORK_ID = "DOWNLOAD_WORK"
    }
}
