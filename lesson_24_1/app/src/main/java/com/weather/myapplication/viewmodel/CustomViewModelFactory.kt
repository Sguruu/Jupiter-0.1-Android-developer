package com.weather.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weather.myapplication.base.di.DiContainer

class CustomViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            AddFragmentViewModel::class.java -> DiContainer.getAddFragmentViewModel() as T
            InfoViewModel::class.java -> DiContainer.getInfoViewModel() as T
            ListViewModel::class.java -> DiContainer.getListViewModel() as T
            else -> error("Ошибка работы фабрики")
        }
    }
}
