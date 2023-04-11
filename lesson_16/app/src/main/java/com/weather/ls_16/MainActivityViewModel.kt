package com.weather.ls_16

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel() : ViewModel() {
    private val _timeLiveData = MutableLiveData<Int>(0)
    val timeLiveData: LiveData<Int>
        get() = _timeLiveData

    fun updateLiveData(time: Int) {
        // работа с данными из потока
        _timeLiveData.postValue(time)
    }
}
