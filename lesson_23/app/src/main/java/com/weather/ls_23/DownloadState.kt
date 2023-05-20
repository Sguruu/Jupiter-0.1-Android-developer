package com.weather.ls_23

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object DownloadState {
    private val mutableDownloadState = MutableStateFlow(false)
    val downloadState: StateFlow<Boolean> = mutableDownloadState

    // изменить состояние
    fun changeDownloadState(isDownloading: Boolean) {
        mutableDownloadState.value = isDownloading
    }
}
