package com.weather.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.weather.myapplication.presentation.utils.SingleLiveEvent

class MainActivityViewModel : ViewModel() {
    private val _isVisibleSearchAction = SingleLiveEvent<Boolean>()
    val isVisibleSearchAction: LiveData<Boolean>
        get() = _isVisibleSearchAction

    fun updateIsVisibleSearchAction(isVisible: Boolean) {
        _isVisibleSearchAction.value = isVisible
    }
}
