package com.weather.myapplication.model.repository

import android.util.Log
import com.weather.myapplication.R
import com.weather.myapplication.base.App
import com.weather.myapplication.base.network.Network
import com.weather.myapplication.model.model.City
import com.weather.myapplication.model.model.Main
import com.weather.myapplication.model.model.ResponseWeather
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
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
        callback: (responseWeather: ResponseWeather?) -> Unit
    ) {
        // запрос асинхронно
        Network.getWeatherCall(lat, lon).enqueue(object : Callback {
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
                    val responseWeather = parseMovieResponse(responseBody)
                    callback.invoke(responseWeather)
                    Log.d("MyTest", "responseBody  $responseBody")
                    // проверим успешно ли выполнился запрос в сеть
                    Log.d("MyTest", "response successful = ${response.isSuccessful}")
                } else {
                    callback.invoke(null)
                }
            }
        })

        // совершим запрос синхронно
//        Thread {
//            try {
//                // создание вызова
//                val response = Network.getWeatherCall(lat, lon)
//                    // выполнение вызова, также он возвращает ответ от сервера
//                    .execute()
//
//                // возвращает тело ответа
//                val responseBody = response.body?.string().orEmpty()
//                val responseWeather = parseMovieResponse(responseBody)
//                callback(responseWeather)
//                Log.d("MyTest", "responseBody  $responseBody")
//                // проверим успешно ли выполнился запрос в сеть
//                Log.d("MyTest", "response successful = ${response.isSuccessful}")
//            } catch (e: IOException) {
//                Log.d("MyTest", "execute request error = ${e.message}", e)
//                callback(null)
//            }
//        }.start()
    }

    private fun parseMovieResponse(responseBodyString: String): ResponseWeather? {
        Log.d("MyTest", "test1")
        return try {
            // создадим объект Object у него уже сможем обращаться к вложенным полям
            // также стоит отметить responseBodyString может придти в не корректном формате
            val jsonObject = JSONObject(responseBodyString)
            val weatherArray = jsonObject.getJSONArray("weather")

            val description = weatherArray.getJSONObject(0).getString("description")
            val tempMin = jsonObject.getJSONObject("main").getDouble("temp_min")
            val tempMax = jsonObject.getJSONObject("main").getDouble("temp_max")

            return ResponseWeather(Main(tempMin, tempMax), description)
        } catch (e: JSONException) {
            Log.d("MyTest", "parse response error = ${e.message}", e)
            null
        }
    }
}
