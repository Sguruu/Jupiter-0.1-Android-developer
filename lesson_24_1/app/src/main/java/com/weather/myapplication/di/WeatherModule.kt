package com.weather.myapplication.di

import com.weather.myapplication.domain.repository.WeatherRepository
import com.weather.myapplication.data.repository.WeatherRepositoryImpl
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
