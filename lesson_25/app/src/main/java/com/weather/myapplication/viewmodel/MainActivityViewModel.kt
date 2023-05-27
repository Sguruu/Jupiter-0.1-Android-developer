package com.weather.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.weather.myapplication.base.ex.SingleLiveEvent

class MainActivityViewModel : ViewModel() {
    private val _isVisibleSearchAction = SingleLiveEvent<Boolean>()
    val isVisibleSearchAction: LiveData<Boolean>
        get() = _isVisibleSearchAction

    fun updateIsVisibleSearchAction(isVisible: Boolean) {
        _isVisibleSearchAction.value = isVisible
    }
}
