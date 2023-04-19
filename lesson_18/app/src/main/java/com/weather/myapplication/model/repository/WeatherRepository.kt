package com.weather.myapplication.model.repository

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.weather.myapplication.R
import com.weather.myapplication.base.App
import com.weather.myapplication.base.network.Network
import com.weather.myapplication.model.model.City
import com.weather.myapplication.model.model.RequestWeather
import com.weather.myapplication.model.model.ResponseWeather
import com.weather.myapplication.model.model.Weather
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
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

    fun requestWeather(
        lat: String,
        lon: String,
        callback: (weather: Weather?) -> Unit
    ): Call {
        // запрос асинхронно
        return Network.getWeatherCall(lat, lon).apply {
            enqueue(object : Callback {
                // если ошибка
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("MyTest", "execute request error = ${e.message}", e)
                    callback.invoke(null)
                }

                // если успешно
                override fun onResponse(call: Call, response: Response) {
                    // проверка что ответ от сервера успешен
                    if (response.isSuccessful) {
                        Network.getWeatherCall(lat, lon)
                            // выполнение вызова, также он возвращает ответ от сервера
                            .execute()
                        // возвращает тело ответа
                        val responseBody = response.body?.string().orEmpty()

                        val weather = parseMovieResponse(responseBody)?.let {
                            Weather(
                                it.main.tempMin,
                                it.main.tempMax,
                                it.weatherCurrent[0].description
                            )
                        }

                        callback.invoke(weather)
                        Log.d("MyTest", "responseBody  $responseBody")
                        // проверим успешно ли выполнился запрос в сеть
                        Log.d("MyTest", "response successful = ${response.isSuccessful}")
                    } else {
                        callback.invoke(null)
                    }
                }
            })
        }
    }

    fun convertWeatherToJson(value: Weather): String {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(RequestWeather::class.java).nonNull()

        try {
            val jsonWeather = adapter.toJson(
                RequestWeather(
                    value.tempMin,
                    value.tempMax,
                    value.descriptionWeather
                )
            )
            return jsonWeather
        } catch (e: Exception) {
            return e.message.toString()
        }
    }

    private fun parseMovieResponse(responseBodyString: String): ResponseWeather? {
        val moshi = Moshi.Builder().build()

        // далее нам нужно создать адаптер для связи данных из JSON с нашим классом
        // .nonNull Возвращает адаптер JSON, эквивалентный этому адаптеру JSON, но не допускающий
        // пустых значений. Если значение null прочитано или записано, это вызовет исключение JsonDataException.
        val adapter = moshi.adapter(ResponseWeather::class.java).nonNull()

        try {
            // может выкинуть ошибку при некорректном JSON или не удалось распарсить JSON
            val responseWeather: ResponseWeather? = adapter.fromJson(responseBodyString)
            return responseWeather
        } catch (e: java.lang.Exception) {
            return null
        }
    }
}
