package com.weather.myapplication.model.repository

import android.util.Log
import com.weather.myapplication.R
import com.weather.myapplication.base.App
import com.weather.myapplication.base.network.Network
import com.weather.myapplication.model.model.City
import java.io.IOException

class WeatherRepository {
    fun createListCity(): List<City> {
        val moscowLink =
            "https://cdn2.tu-tu.ru/image/pagetree_node_data/1/055c4b01273eb986fead1a6db4c20e9f/"
        val samaraLink =
            "https://247510.selcdn.ru/mybiz_production/posts/images/posters/cadb20593bcd701d4c42f40132f416928fe30f12.jpg"
        val sakhalinLink = "https://top10.travel/wp-content/uploads/2017/10/mys-mayak-aniva.jpg"

        return listOf(
            City(
                name = App.res.getString(R.string.moscow),
                imageLink = moscowLink,
                lat = "37.6024",
                lon = "55.7367"
            ),
            City(
                name = App.res.getString(R.string.samara),
                imageLink = samaraLink,
                lat = "53.186457",
                lon = "50.119760"
            ),
            City(
                name = App.res.getString(R.string.sakhalin),
                imageLink = sakhalinLink,
                lat = "46.959746",
                lon = "142.731299"
            )
        )
    }

    fun responseWeather() {}

    fun requestWeather(lat: String, lon: String) {
        // совершим запрос синхронно
        Thread {
            try {
                // создание вызова
                val response = Network.getWeatherCall(lat, lon)
                    // выполнение вызова, также он возвращает ответ от сервера
                    .execute()

                // проверим успешно ли выполнился запрос в сеть
                Log.d("MyTest", "response successful = ${response.isSuccessful}")
            } catch (e: IOException) {
                Log.d("MyTest", "execute request error = ${e.message}", e)
            }
        }.start()
    }
}
