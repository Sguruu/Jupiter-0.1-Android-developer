package com.weather.myapplication.di

import com.weather.myapplication.data.mapper.MapperCity
import com.weather.myapplication.data.mapper.MapperWeather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MapperModule {

    @Provides
    fun provideMapperCity(): MapperCity {
        return MapperCity()
    }

    @Provides
    fun provideMapperWeather(): MapperWeather {
        return MapperWeather()
    }
}
