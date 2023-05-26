package com.weather.myapplication.base.di

import com.weather.myapplication.base.db.DataBase
import com.weather.myapplication.repository.WeatherRepository
import com.weather.myapplication.repository.WeatherRepositoryImpl
import com.weather.myapplication.viewmodel.AddFragmentViewModel
import com.weather.myapplication.viewmodel.InfoViewModel
import com.weather.myapplication.viewmodel.ListViewModel

object DiContainer {
    fun getAddFragmentViewModel(): AddFragmentViewModel {
        return AddFragmentViewModel(getWeatherRepository())
    }

    fun getInfoViewModel(): InfoViewModel {
        return InfoViewModel(getWeatherRepository())
    }

    fun getListViewModel(): ListViewModel {
        return ListViewModel(getWeatherRepository())
    }

    private fun getWeatherRepository(): WeatherRepository {
        return WeatherRepositoryImpl(DataBase.instance.CityDao())
    }
}
