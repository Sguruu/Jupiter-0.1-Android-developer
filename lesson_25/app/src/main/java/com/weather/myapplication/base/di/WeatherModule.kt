package com.weather.myapplication.base.di

import com.weather.myapplication.repository.WeatherRepository
import com.weather.myapplication.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class WeatherModule {

//    @Provides
//    fun providesUserRepository(impl: WeatherRepositoryImpl): WeatherRepository {
//        return impl
//    }

    // можем вместо Provides использовать Binds в абстрактном классе, тогда dagger сам реализует
    // абстракную функцию
    @Binds
    // @ViewModelScoped
    abstract fun providesUserRepository(impl: WeatherRepositoryImpl): WeatherRepository
}
